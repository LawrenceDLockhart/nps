package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class SurveyView extends VerticalLayout {

    private final SurveyService surveyService;

    public SurveyView(SurveyService surveyService) {
        this.surveyService = surveyService;
        List<Answer> answers = new ArrayList<>();
        List<Binder<Answer>> binders = new ArrayList<>(); // List to store Binders

        H2 title = new H2("NPS Survey");
        add(title);
        List<Question> questions = surveyService.getAllQuestions();

        for (Question question : questions) {
            RadioButtonGroup<Integer> score = new RadioButtonGroup<>();
            score.setItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            score.setLabel(question.getText());
            add(score);

            Answer answer = new Answer();
            answers.add(answer);

            Binder<Answer> binder = new Binder<>(Answer.class);
            binder.forField(score)
                    .asRequired("Please select a score")
                    .bind(Answer::getScore, Answer::setScore);
            binders.add(binder); // Add the Binder to the list
        }

        Button submitButton = new Button("Submit", event -> {
            boolean allQuestionsAnswered = true;

            for (int i = 0; i < questions.size(); i++) {
                Answer answer = answers.get(i);
                Binder<Answer> binder = binders.get(i);

                if (binder.writeBeanIfValid(answer)) {
                    Question question = questions.get(i);
                    question.addAnswer(answer);
                    surveyService.saveAnswer(answer);
                } else {
                    allQuestionsAnswered = false;
                }
            }

            if (allQuestionsAnswered) {
                Notification.show("Thank you for your feedback!");
                getUI().ifPresent(ui -> ui.navigate("results"));
            } else {
                Notification.show("Please fill in all required fields.");
            }
        });
        add(submitButton);

        RouterLink resultsLink = new RouterLink("View Results", ResultsView.class);
        add(resultsLink);
    }
}