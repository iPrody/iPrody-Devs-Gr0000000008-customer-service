package com.iprody08.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryCodeDTO {

    private String id;

    private String countryName;

    private CountryDTO countryDTO;

    private Instant createdAt;

    private Instant updatedAt;

}
