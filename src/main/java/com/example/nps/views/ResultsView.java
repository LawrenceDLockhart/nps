package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.html.H2;
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

        List<Answer> answers = surveyService.getAllAnswers();
        double npsScore = surveyService.calculateNPS(answers);

        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("NPS Score");
        ListSeries series = new ListSeries("NPS", npsScore);
        conf.addSeries(series);

        add(chart);
    }
}