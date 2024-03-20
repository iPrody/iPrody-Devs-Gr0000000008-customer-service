package com.iprody08.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private Long id;

    private CountryCodeDTO countryCodeDTO;

    private String countryName;

    private List<CustomerDTO> customersDTO;

    private Instant createdAt;

    private Instant updatedAt;

}
