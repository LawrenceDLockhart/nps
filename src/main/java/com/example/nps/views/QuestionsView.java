package com.example.nps.views;

import com.example.nps.entities.Question;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("questions")
public class QuestionsView extends AppLayout {

    private final SurveyService surveyService;
    private final Grid<Question> questionGrid;
    private final Binder<Question> questionBinder;

    public QuestionsView(SurveyService surveyService) {
        this.surveyService = surveyService;

        createHeader();
        createDrawer();

        questionGrid = new Grid<>(Question.class, false);
        questionGrid.addColumn(Question::getText).setHeader("Question Text");
        questionGrid.setItems(surveyService.getAllQuestions());

        questionBinder = new Binder<>(Question.class);
        TextField questionField = new TextField("New Question");
        questionBinder.forField(questionField)
                .asRequired("Question text is required")
                .bind(Question::getText, Question::setText);

        Button addQuestionButton = new Button("Add Question", event -> {
            Question newQuestion = new Question();
            if (questionBinder.writeBeanIfValid(newQuestion)) {
                surveyService.saveQuestion(newQuestion);
                questionGrid.setItems(surveyService.getAllQuestions());
                questionField.clear();
                questionBinder.refreshFields();
            }
        });

        ComboBox<String> typeSelector = new ComboBox<>("Question Type");
        typeSelector.setItems("1-10 Scale", "Open Text");
        questionBinder.forField(typeSelector)
                .asRequired("Question type is required")
                .bind(Question::getQuestionType, Question::setQuestionType);

        HorizontalLayout questionLayout = new HorizontalLayout(questionField, typeSelector, addQuestionButton);
        questionLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        // Set the content of the AppLayout
        setContent(new VerticalLayout(questionLayout, questionGrid));
    }

    private void createHeader() {
        H2 title = new H2("Question Dashboard");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink questionsLink = new RouterLink("Questions", QuestionsView.class);
        RouterLink resultsLink = new RouterLink("Results", ResultsView.class);
        RouterLink surveyLink = new RouterLink("Survey", SurveyView.class);
        addToDrawer(new VerticalLayout(questionsLink, resultsLink, surveyLink));
    }
}