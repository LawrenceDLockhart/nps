package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.entities.QuestionType;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

@Route("survey")
public class SurveyView extends VerticalLayout {

    private final SurveyService surveyService;

    public SurveyView(SurveyService surveyService) {
        this.surveyService = surveyService;
        List<Answer> answers = new ArrayList<>();
        List<Binder<Answer>> binders = new ArrayList<>();
        H2 title = new H2("Survey");

        List<Question> questions = surveyService.getAllQuestions();
        for (Question question : questions) {
            if (question.getQuestionType().equals(QuestionType.ONE_TO_TEN_SCALE)){
                RadioButtonGroup<Integer> score = new RadioButtonGroup<>();
                score.setItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                score.setLabel(question.getText());
                add(score);

                Answer answer = new Answer();
                answer.setQuestion(question);
                answers.add(answer);
                Binder<Answer> binder = new Binder<>(Answer.class);
                binder.forField(score)
                        .asRequired("Please select a score")
                        .bind(Answer::getScore, Answer::setScore);
                binders.add(binder);
            } else if (question.getQuestionType().equals(QuestionType.OPEN_TEXT)) {
                TextField userResponse = new TextField();
                userResponse.setLabel(question.getText());
                add(userResponse);
                Answer answer = new Answer();
                answer.setQuestion(question);
                answers.add(answer);

                Binder<Answer> binder = new Binder<>(Answer.class);
                binder.forField(userResponse)
                        .asRequired("Please enter your answer")
                        .bind(Answer::getTextAnswer, Answer::setTextAnswer);
                binders.add(binder);
            }

        }

        Button submitButton = new Button("Submit", event -> {
            boolean allQuestionsAnswered = true;

            for (int i = 0; i < questions.size(); i++) {
                Answer answer = answers.get(i);
                Binder<Answer> binder = binders.get(i);

                if (binder.writeBeanIfValid(answer)) {
                    Question question = questions.get(i);
                    answer.setQuestion(question);
                    surveyService.saveAnswer(answer);
                } else {
                    allQuestionsAnswered = false;
                }
            }
            if (allQuestionsAnswered) {
                for (Binder<Answer> binder : binders) {
                    binder.refreshFields();
                }
                removeAll();
                add(new H2("Thank you for your feedback!"));
//                getUI().ifPresent(ui -> ui.navigate("results"));
            } else {
                Notification.show("Please fill in all required fields.");
            }
        });

        add(title, submitButton);

        RouterLink resultsLink = new RouterLink("View Results", ResultsView.class);
        add(resultsLink);
    }
}