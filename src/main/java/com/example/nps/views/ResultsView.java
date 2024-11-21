package com.example.nps.views;

import com.example.nps.entities.Answer;
import com.example.nps.entities.Question;
import com.example.nps.services.SurveyService;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("results")
public class ResultsView extends VerticalLayout {

    private final SurveyService surveyService;

    public ResultsView(SurveyService surveyService) {
        this.surveyService = surveyService;

        H2 title = new H2("Survey Results");
        add(title);

        List<Question> questions = surveyService.getAllQuestions();
        if (questions != null && !questions.isEmpty()) {

            Chart chart = new Chart(ChartType.COLUMN);
            Configuration conf = chart.getConfiguration();
            conf.setTitle("Survey Results");

            XAxis xAxis = new XAxis();
            xAxis.setTitle("Responses");
            conf.addxAxis(xAxis);

            YAxis yAxis = new YAxis();
            yAxis.setMin(0);
            yAxis.setMax(10);
            yAxis.setTitle("Score");
            conf.addyAxis(yAxis);

            for (Question question : questions) {
                if (question.getQuestionType().equals("radio")) {
                    List<DataSeriesItem> dataPoints = surveyService.getScoreDataPoints(question);

                    DataSeries series = new DataSeries(question.getText());
                    series.setData(dataPoints);
                    conf.addSeries(series);
                }
            }
            add(chart);
        }
    }
}