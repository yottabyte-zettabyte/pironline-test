package com.pironline.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePatchInputDto {

    @PositiveOrZero(message = "Field 'version' should be bigger than or equal to 0")
    private long version;

    private UUID newCompanyId;

    private Integer newTitleId;

    private LocalDate startDate;

    private LocalDate leaveDate;

    private BigDecimal salaryAmount;
}
