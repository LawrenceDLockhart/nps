package com.example.nps.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class AdminView extends MainLayout {

    public AdminView() {
        setContent(new Div("Admin View"));
//        createHeader();
//        createDrawer();
//        createMainContent();
    }
//    private void createHeader() {
//        H2 title = new H2("Admin Panel");
//        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
//        addToNavbar(header);
//    }
//
//    private void createDrawer() {
//        RouterLink questionsLink = new RouterLink("Questions", QuestionsView.class);
//        RouterLink resultsLink = new RouterLink("Results", ResultsView.class);
//        RouterLink surveyLink = new RouterLink("Survey", SurveyView.class);
//        addToDrawer(new VerticalLayout(questionsLink, resultsLink, surveyLink));
//    }
//
//    private void createMainContent() {
//
//    }
}