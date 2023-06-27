package com.example.fakeapiserver.controller;

import com.example.fakeapiserver.entity.APIResponse;
import com.example.fakeapiserver.entity.Question;
import com.example.fakeapiserver.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/questions")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("questions", quizService.findAll()))
                        .build(), OK);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("question", quizService.findById(id)))
                        .build(), OK);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid Question question) {
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("question", quizService.insert(question)))
                        .build(), OK);
    }

    @PostMapping("/all")
    public ResponseEntity<?> insertAll(@RequestBody List<Question> questions) {
        System.out.println(questions);
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("questions", quizService.insertAll(questions)))
                        .build(), OK);
    }
}
