package com.pironline.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    @NotBlank(message = "Field 'shortName' should not be empty")
    @Size(max = 100, message = "Size of 'shortName' field should not be bigger than 100")
    private String shortName;

    @NotBlank(message = "Field 'longName' should not be empty")
    @Size(max = 100, message = "Size of 'longName' field should not be bigger than 100")
    private String longName;

    private String description;

    @NotBlank(message = "Field 'inn' should not be empty")
    @Size(max = 20, message = "Size of 'inn' field should not be bigger than 20")
    private String inn;
}
