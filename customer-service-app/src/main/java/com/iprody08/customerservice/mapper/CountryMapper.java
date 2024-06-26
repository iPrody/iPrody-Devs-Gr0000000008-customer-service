package com.iprody08.customerservice.mapper;

import com.iprody08.customerservice.dto.CountryDto;
import com.iprody08.customerservice.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Country dtoToCountry(CountryDto countryDto);

    CountryDto countryToDto(Country country);

}
