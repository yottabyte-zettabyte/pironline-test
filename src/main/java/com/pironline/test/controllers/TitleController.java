package com.pironline.test.controllers;

import com.pironline.test.dto.TitleDto;
import com.pironline.test.service.TitleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/titles")
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getAllTitles() {
        List<TitleDto> titles = titleService.getAllAsList();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }
}
