package com.pironline.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyFullDto extends CompanyDto {

    private UUID id;

    @Min(value = 0, message = "Field 'version' should be bigger than or equal to 0")
    private long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
