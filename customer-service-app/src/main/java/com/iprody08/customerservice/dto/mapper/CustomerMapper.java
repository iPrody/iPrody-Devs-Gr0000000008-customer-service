package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.entities.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "name", source = "customerName")
    @Mapping(target = "surname", source = "customerSurname")
    @Mapping(target = "country", source = "countryDTO")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    @InheritInverseConfiguration
    CustomerDTO customerToCustomerDTO(Customer customer);

}
