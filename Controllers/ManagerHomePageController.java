package com.example.ifbproject.Controllers;

import com.example.ifbproject.Database.DatabaseInfo;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/17/2022
 *
 * ManagerHomePage Controller. Used to display employees efficiency
 */

public class ManagerHomePageController {
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private Label monthEfficiency;
    @FXML
    private Label weekEfficiency;
    @FXML
    private Label dayEfficiency;
    @FXML
    private ChoiceBox<String> weekChoiceBox;
    @FXML
    private ChoiceBox<String> userChoiceBox;
    
    private static String nameFromChoiceBox = "";
    private int weekFromChoiceBox = 0;
    
    public void initialize() throws SQLException {
        //getting all the userNames from the database
        ArrayList<String> userNames = new ArrayList<>();
        //sql to get all the names from the database
        String selectAllUserNames = "Select \"name\" from users";
        PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(selectAllUserNames);
        ResultSet resultSet = preparedStatement.executeQuery();
        //adding userNames to userNames
        while(resultSet.next()){
            userNames.add(resultSet.getString("name"));
        }
        //removes manager from the list and loads the other names into the choiceBox
        userNames.removeIf(str -> str.equals("Manager"));
        loadData(userNames);
        
        //calls the methods when the choice box is updated
        userChoiceBox.setOnAction(this::getNameFromChoiceBox);
        weekChoiceBox.setOnAction(this::getWeekFromChoiceBox);
    }
    
    //loads the usernames and weeks into the choiceBoxes
    private void loadData(ArrayList<String> userNames){
        userChoiceBox.getItems().addAll(userNames);
        
        weekChoiceBox.getItems().addAll("Week 1", "Week 2", "Week 3", "Week 4");
    }
    
    //takes you to the ManagerWeeklyPage
    @FXML
    private void weeklyPage(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/com/example/ifbproject/Views/ManagerWeeklyPage.fxml").toURI().toURL();
    
        Parent infoParent = FXMLLoader.load(url);
        Scene infoScene = new Scene(infoParent);
    
        Stage window = (Stage) myMenuBar.getScene().getWindow();
    
        window.setScene(infoScene);
        window.show();
    }
    
    //gets the name chosen from the username choice box and calculates the day, week, and month efficiency
    private void getNameFromChoiceBox(ActionEvent actionEvent) {
        nameFromChoiceBox = userChoiceBox.getValue();
    
        //sets the day efficiency based on the user selected. lastWeek always gives the last week
        int lastWeek = 4;
        try {
            dayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(nameFromChoiceBox, lastWeek, DaysOfTheWeek.FRIDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        dayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(dayEfficiency.getText().substring(0, dayEfficiency.getText().length() - 1)) < 90)
            dayEfficiency.setTextFill(Color.RED);
        
        //if a new username is chosen this will make sure that the week efficiency is updated
        if(weekFromChoiceBox != 0){
            try {
                weekEfficiency.setText(EfficiencyCalculator.weekEfficiencyCalculation(nameFromChoiceBox, weekFromChoiceBox));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //sets the color to green if efficiency is above 90% and red otherwise
            weekEfficiency.setTextFill(Color.GREEN);
            if(Double.parseDouble(weekEfficiency.getText().substring(0, weekEfficiency.getText().length() - 1)) < 90)
                weekEfficiency.setTextFill(Color.RED);
        }
        
        //sets the month efficiency based on the user selected
        try {
            monthEfficiency.setText(EfficiencyCalculator.monthEfficiencyCalculation(nameFromChoiceBox));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        monthEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(monthEfficiency.getText().substring(0, monthEfficiency.getText().length() - 1)) < 90)
            monthEfficiency.setTextFill(Color.RED);
    }
    
    //gets the week chosen from the week choiceBox and calculates that weeks efficiency
    private void getWeekFromChoiceBox(ActionEvent actionEvent) {
        String weekString = weekChoiceBox.getValue();
        switch (weekString) {
            case "Week 1" -> weekFromChoiceBox = 1;
            case "Week 2" -> weekFromChoiceBox = 2;
            case "Week 3" -> weekFromChoiceBox = 3;
            case "Week 4" -> weekFromChoiceBox = 4;
        }
        
        //sets the week efficiency based on the user selected and the week selected
        try {
            weekEfficiency.setText(EfficiencyCalculator.weekEfficiencyCalculation(nameFromChoiceBox, weekFromChoiceBox));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        weekEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(weekEfficiency.getText().substring(0, weekEfficiency.getText().length() - 1)) < 90)
            weekEfficiency.setTextFill(Color.RED);
    }
    
    // ############## Getter ##############
    public static String getNameFromChoiceBox() {
        return nameFromChoiceBox;
    }
}