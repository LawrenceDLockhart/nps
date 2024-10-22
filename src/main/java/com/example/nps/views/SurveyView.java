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

@Route("") 
public class SurveyView extends VerticalLayout {

    private final SurveyService surveyService;

    public SurveyView(SurveyService surveyService) {
        this.surveyService = surveyService;

        H2 title = new H2("NPS Survey");
        add(title);
        Question question = surveyService.getQuestion(1L);

        RadioButtonGroup<Integer> score = new RadioButtonGroup<>();
        score.setItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        score.setLabel(question.getText());
        add(score);

        Binder<Answer> binder = new Binder<>(Answer.class);
        binder.forField(score)
                .asRequired("Please select a score")
                .bind(Answer::getScore, Answer::setScore);

        Button submitButton = new Button("Submit", event -> {
            Answer answer = new Answer();
            if (binder.writeBeanIfValid(answer)) {

                question.addAnswer(answer);
                surveyService.saveAnswer(answer);
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
