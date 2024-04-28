package com.pironline.test.mappers;

import com.pironline.test.dto.TitleDto;
import com.pironline.test.persistences.Title;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TitleMapper {

    TitleDto entityToDto(Title entity);

    List<TitleDto> entitiesToDtos(List<Title> entities);
}
