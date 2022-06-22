package com.example.ifbproject.Controllers;

import com.example.ifbproject.Model.EfficiencyCalculator;
import com.example.ifbproject.Utils.DaysOfTheWeek;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/16/2022
 *
 * Uses the EfficiencyCalculator to calculate and show the user their day, week, and month efficiency
 */

public class EfficiencyPageController {
    @FXML
    private ChoiceBox<String> weekChoiceBox;
    @FXML
    private Label weekEfficiency;
    @FXML
    private Label monthEfficiency;
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private Label name;
    @FXML
    private Label dayEfficiency;
    
    //sets weekFromChoiceBox equal to week 4
    private int weekFromChoiceBox = 4; //0
    
    public void initialize() throws SQLException {
        //sets the name in the scene
        name.setText(LoginPageController.getName());
        
        //loads data into choice box
        loadData();
        
        //calls EfficiencyCalculator method to calculate day efficiency. lastWeek always gives the last week
        int lastWeek = 4;
        dayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), lastWeek, DaysOfTheWeek.FRIDAY.getDay()));
        //sets the color to green if efficiency is above 90% and red otherwise
        dayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(dayEfficiency.getText().substring(0, dayEfficiency.getText().length() - 1)) < 90)
            dayEfficiency.setTextFill(Color.RED);
    
        //calls method when choice box is updated
        weekChoiceBox.setOnAction(this::getWeek);
        
        //calls EfficiencyCalculator method to calculate month efficiency
        monthEfficiency.setText(EfficiencyCalculator.monthEfficiencyCalculation(name.getText()));
        //sets the color to green if efficiency is above 90% and red otherwise
        monthEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(monthEfficiency.getText().substring(0, monthEfficiency.getText().length() - 1)) < 90)
            monthEfficiency.setTextFill(Color.RED);
    }
    
    //loading the choiceBox with week 1 through week 4
    private void loadData() throws SQLException {
        weekChoiceBox.getItems().addAll("Week 1", "Week 2", "Week 3", "Week 4");
        weekChoiceBox.setValue("Week 4");
        
//        //sets weekNumberUsedForArrayList equal to week 4
//        weekFromChoiceBox = 3;
        
        //preload the efficiency page with week 4 efficiency
        weekEfficiency.setText(EfficiencyCalculator.weekEfficiencyCalculation(name.getText(), weekFromChoiceBox));
        //sets the color to green if efficiency is above 90% and red otherwise
        weekEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(weekEfficiency.getText().substring(0, weekEfficiency.getText().length() - 1)) < 90)
            weekEfficiency.setTextFill(Color.RED);
    }
    
    //Changes back to the counter page
    @FXML
    private void counterPage(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/com/example/ifbproject/Views/CounterPage.fxml").toURI().toURL();
    
        Parent infoParent = FXMLLoader.load(url);
        Scene infoScene = new Scene(infoParent);
    
        Stage window = (Stage) myMenuBar.getScene().getWindow();
    
        window.setScene(infoScene);
        window.show();
    }
    
    //gets the week chosen from the week choiceBox and calculates that weeks efficiency
    private void getWeek(ActionEvent actionEvent) {
        String weekString = weekChoiceBox.getValue();
        switch (weekString) {
            case "Week 1" -> weekFromChoiceBox = 1;
            case "Week 2" -> weekFromChoiceBox = 2;
            case "Week 3" -> weekFromChoiceBox = 3;
            case "Week 4" -> weekFromChoiceBox = 4;
        }
    
        try {
            weekEfficiency.setText(EfficiencyCalculator.weekEfficiencyCalculation(name.getText(), weekFromChoiceBox));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        weekEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(weekEfficiency.getText().substring(0, weekEfficiency.getText().length() - 1)) < 90)
            weekEfficiency.setTextFill(Color.RED);
    }
}