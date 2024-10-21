package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("results")
public class ResultsView extends VerticalLayout {

    private final SurveyService surveyService;

    public ResultsView(SurveyService surveyService) {
        this.surveyService = surveyService;

        H2 title = new H2("NPS Results");
        add(title);

        List<Answer> answers = surveyService.getAllAnswers(); // Fetch all answers
        double npsScore = surveyService.calculateNPS(answers);

        Paragraph npsDisplay = new Paragraph("NPS Score: " + npsScore);
        add(npsDisplay);

        // You can add more components here to display other statistics,
        // such as the number of promoters, detractors, and passives.
    }
}