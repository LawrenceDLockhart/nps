package com.example.nps.services;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.entities.AnswerRepository;
import com.example.nps.entities.QuestionRepository;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return question.orElse(null);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public List<Answer> getAnswersForQuestion(Question question) {
        return answerRepository.findByQuestion(question);
    }

    public List<DataSeriesItem> getScoreDataPoints(Question question) {
        List<Answer> answers = getAnswersForQuestion(question);
        List<DataSeriesItem> dataPoints = new ArrayList<>();

        for (Answer answer : answers) {
                DataSeriesItem item = new DataSeriesItem();
                item.setY(answer.getScore());
                dataPoints.add(item);
        }

        return dataPoints;
    }
}