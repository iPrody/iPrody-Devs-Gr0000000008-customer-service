package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.dto.mapper.CustomerMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import com.iprody08.customerservice.services.CustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String ERROR_CUSTOMER_EXISTS_MESSAGE = "Customer %s already exists ";
    private static final String ERROR_CUSTOMER_NOT_FOUND_MESSAGE = "Customer with id %d not found";
    private static final String ERROR_COUNTRY_NOT_FOUND_MESSAGE = "Country with id %d not found";

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CountryRepository countryRepository,
                               ContactDetailsRepository contactDetailsRepository,
                               CustomerMapper customerMapper
                               ) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        this.contactDetailsRepository = contactDetailsRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerDto addCustomer(CustomerDto dto) {
        if (dto.getId() != null && customerRepository.existsById(dto.getId())) {
            log.error(String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, dto.getId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getId()));
        }

        if (contactDetailsRepository.findByTelegramId(dto.getTelegramId()).isPresent()) {
            log.error(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getTelegramId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getTelegramId()));
        }

        if (contactDetailsRepository.findByEmail(dto.getEmail()).isPresent()) {
            log.error(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getEmail()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getEmail()));
        }

        if (!countryRepository.existsById(dto.getCountryId())) {
            log.error(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId()));
            throw new EntityNotFoundException(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId()));
        }

        Customer customer = customerMapper.dtoToCustomer(dto);
        ContactDetails contactDetails = customer.getContactDetails();
        contactDetailsRepository.save(contactDetails);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToDTO(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(CustomerDto dto) {
        return customerRepository.findById(dto.getId())
                .map(customer -> {
                    customer.setName(dto.getName());
                    customer.setSurname(dto.getSurname());
                    if (dto.getCountryId() != null) {
                        Country country = countryRepository.findById(dto.getCountryId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId())));
                        customer.setCountry(country);
                    }
                    if (contactDetailsRepository.findByTelegramId(dto.getTelegramId()).isEmpty()) {
                        customer.getContactDetails().setTelegramId(dto.getTelegramId());
                    }
                    if (contactDetailsRepository.findByEmail(dto.getEmail()).isEmpty()) {
                        customer.getContactDetails().setEmail(dto.getEmail());
                    }

                    customer.getContactDetails().setUpdatedAt(Instant.now());
                    customer.setUpdatedAt(Instant.now());

                    Customer savedCustomer = customerRepository.save(customer);
                    return customerMapper.customerToDTO(savedCustomer);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, dto.getId())));
    }

    @Override
    public Optional<CustomerDto> findCustomerById(long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> findCustomerByEmail(String email) {
        return customerRepository.findByContactDetailsEmail(email)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public Optional<CustomerDto> findCustomerByTelegramId(String telegramId) {
        return customerRepository.findByContactsDetailsTelegramId(telegramId)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public List<CustomerDto> findCustomerByName(String name, Pageable pageable) {
        return customerRepository.findCustomersByName(name).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> findCustomerBySurname(String surname, Pageable pageable) {
        return customerRepository.findCustomersBySurname(surname).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> findCustomersByCountry(String country, Pageable pageable) {
        return customerRepository.findCustomersByCountry(country).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

}
