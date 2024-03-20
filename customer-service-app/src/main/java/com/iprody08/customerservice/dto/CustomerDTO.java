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
public class CustomerDTO {

    private Long id;

    private String customerName;

    private String customerSurname;

    private CountryDTO countryDTO;

    private ContactDetailsDTO contactDetails;

    private Instant createdAt;

    private Instant updatedAt;
}
