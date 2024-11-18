package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("admin")
public class AdminView extends VerticalLayout {

    private final SurveyService surveyService;
    private final Grid<Question> questionGrid;
    private final Binder<Question> questionBinder;

    public AdminView(SurveyService surveyService) {
        this.surveyService = surveyService;

        H2 title = new H2("Admin Panel");
        add(title);

        // Question Management
        questionGrid = new Grid<>(Question.class, false);
        questionGrid.addColumn(Question::getText).setHeader("Question Text");
        questionGrid.setItems(surveyService.getAllQuestions());

        questionBinder = new Binder<>(Question.class);
        TextField questionField = new TextField("New Question");
        questionBinder.forField(questionField).asRequired("Question text is required").bind(Question::getText, Question::setText);

        Button addQuestionButton = new Button("Add Question", event -> {
            Question newQuestion = new Question();
            if (questionBinder.writeBeanIfValid(newQuestion)) {
                surveyService.saveQuestion(newQuestion);
                questionGrid.setItems(surveyService.getAllQuestions());
                questionField.clear();
            }
        });

        HorizontalLayout questionLayout = new HorizontalLayout(questionField, addQuestionButton);
        add(questionLayout, questionGrid);
    }
}