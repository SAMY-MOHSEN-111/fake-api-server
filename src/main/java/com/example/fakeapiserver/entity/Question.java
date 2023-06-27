package com.example.fakeapiserver.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String id;
    @NotBlank(message = "question can't be left empty")
    private String question;
    @NotEmpty(message = "you should put at least 2 options")
    private List<String> options;
    @NotNull(message = "correct option must be specified")
    private Integer correctOption;
    @NotNull(message = "points can't be empty")
    private Integer points;
}
