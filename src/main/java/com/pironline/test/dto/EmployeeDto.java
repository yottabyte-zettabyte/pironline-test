package com.pironline.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pironline.test.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {

    @NotBlank(message = "Field 'firstName' should not be empty")
    @Size(max = 100, message = "Size of 'firstName' field should not be bigger than 100")
    private String firstName;

    @NotBlank(message = "Field 'lastName' should not be empty")
    @Size(max = 100, message = "Size of 'lastName' field should not be bigger than 100")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Field 'snils' should not be empty")
    @Size(max = 20, message = "Size of 'snils' field should not be bigger than 20")
    private String snils;

    private LocalDate birthDate;

    @NotNull(message = "Field 'gender' should not be empty")
    private Gender gender;

    @NotNull(message = "Field 'companyId' should not be empty")
    private UUID companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer titleId;

    private LocalDate startDate;

    private LocalDate leaveDate;

    private BigDecimal salaryAmount;
}
