package com.pironline.test.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TitleDto {

    @EqualsAndHashCode.Include
    private Integer id;

    private String description;
}
