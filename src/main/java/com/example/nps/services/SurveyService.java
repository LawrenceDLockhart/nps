package com.example.nps.services;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.entities.AnswerRepository;
import com.example.nps.entities.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public SurveyService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Question getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null); // Or handle the case where the question is not found
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }
    public double calculateNPS(List<Answer> answers) {
        if (answers == null || answers.isEmpty()) {
            return 0; // Handle empty input
        }

        int promoters = 0;
        int detractors = 0;
        int totalResponses = answers.size();

        for (Answer answer : answers) {
            int score = answer.getScore();
            if (score >= 9) {
                promoters++;
            } else if (score <= 6) {
                detractors++;
            }
        }

        double promoterPercentage = (double) promoters / totalResponses * 100;
        double detractorPercentage = (double) detractors / totalResponses * 100;

        return promoterPercentage - detractorPercentage;
    }
}