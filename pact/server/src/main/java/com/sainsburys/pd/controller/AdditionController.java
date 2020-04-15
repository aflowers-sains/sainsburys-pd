package com.sainsburys.pd.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class AdditionController {
    @GetMapping("/add")
    private ResponseEntity<Map<String, Integer>> add(@RequestParam("first") int first, @RequestParam("second") int second) {
        if(first < 0 || second < 0) {
            throw new IllegalArgumentException("cannot do negative numbers");
        }

        return ResponseEntity.ok(Map.of("answer", first + second));
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Map<String, String>> handleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
