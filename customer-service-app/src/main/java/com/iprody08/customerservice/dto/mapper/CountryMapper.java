package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.CountryDTO;
import com.iprody08.customerservice.entities.Country;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "countryCode", source = "countryCodeDTO")
    @Mapping(target = "name", source = "countryName")
    @Mapping(target = "customer", source = "customersDTO")
    Country countryDTOToCountry(CountryDTO countryDTO);

    @InheritInverseConfiguration
    CountryDTO countryToCountryDTO(Country country);

}
