package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.ContactDetailsDTO;
import com.iprody08.customerservice.entities.ContactDetails;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactDetailsMapper {

    ContactDetailsMapper INSTANCE = Mappers.getMapper(ContactDetailsMapper.class);

    @Mapping(target = "customer", source = "customerDTO")
    ContactDetails contactDetailsDTOToContactDetails(ContactDetailsDTO contactDetailsDTO);

    @InheritInverseConfiguration
    ContactDetailsDTO contactDetailsToContactDetailsDTO(ContactDetails contactDetails);

}
