package com.pironline.test.services;

import com.pironline.test.dto.TitleDto;
import com.pironline.test.mappers.TitleMapper;
import com.pironline.test.persistences.Title;
import com.pironline.test.repositories.TitleRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleMapper titleMapper;
    private final TitleRepository titleRepository;

    private List<TitleDto> titlesList;
    private Map<Integer, TitleDto> titlesMap;

    @PostConstruct
    public void init() {
        List<Title> entities = titleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        titlesList = titleMapper.entitiesToDtos(entities);
        titlesMap = titlesList.stream().collect(Collectors.toMap(TitleDto::getId, Function.identity()));
    }

    public List<TitleDto> getAllAsList() {
        return new ArrayList<>(titlesList);
    }

    public TitleDto getById(Integer titleId) {
        return titlesMap.get(titleId);
    }
}
