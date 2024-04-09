package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContactDetailsService {

    ContactDetailsDto save(ContactDetailsDto contactDetailsDTO);

    ContactDetailsDto update(ContactDetailsDto contactDetailsDTO);
    Optional<ContactDetailsDto> findContactsById(long id);

    void delete(long id);

    List<ContactDetailsDto> findAll(Pageable pageable);

}
