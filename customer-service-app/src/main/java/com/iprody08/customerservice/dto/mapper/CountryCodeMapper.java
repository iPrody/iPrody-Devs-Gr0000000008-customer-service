package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.CountryCodeDTO;
import com.iprody08.customerservice.entities.CountryCode;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CountryCodeMapper {

    CountryCodeMapper INSTANCE = Mappers.getMapper(CountryCodeMapper.class);

    @Mapping(source = "countryName", target = "name")
    @Mapping(source = "countryDTO", target = "country")
    CountryCode countryCodeDTOToCountryCode(CountryCodeDTO countryCodeDTO);

    @InheritInverseConfiguration
    CountryCodeDTO countryCodeToCountryCodeDTO(CountryCode countryCode);

}
