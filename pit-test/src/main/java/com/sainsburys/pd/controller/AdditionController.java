package com.sainsburys.pd.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class AdditionController {
    @GetMapping("/add")
    private Map<String, Integer> add(@RequestParam("first") int first, @RequestParam("second") int second) {
        return Map.of("answer", first + second);
    }
}
