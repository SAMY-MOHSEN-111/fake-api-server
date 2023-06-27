package com.example.fakeapiserver.service;

import com.example.fakeapiserver.entity.Question;
import com.example.fakeapiserver.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final MessageSource messageSource;
    private final QuizRepository quizRepository;

    public Question findById(String id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageSource
                        .getMessage("validation.constraint.question-id.message",
                                null,
                                LocaleContextHolder.getLocale()
                        )));
    }

    public List<Question> findAll() {
        return quizRepository.findAll();
    }

    public Question insert(Question question) {
        return quizRepository.insert(question);
    }

    public List<Question> insertAll(List<Question> questions) {
        return quizRepository.insert(questions);
    }
}
