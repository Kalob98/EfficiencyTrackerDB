package com.example.ifbproject.Controllers;

import com.example.ifbproject.Model.EfficiencyCalculator;
import com.example.ifbproject.Model.UserDayTotal;
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
 * Last edited: 6/17/2022
 *
 * ManagerWeeklyPage Controller. Used to display a users total amount of items made and the efficiency for each day for
 * any given week
 */

public class ManagerWeeklyPageController {
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private ChoiceBox<String> weekChoiceBox;
    @FXML
    private Label mondayTotal;
    @FXML
    private Label mondayEfficiency;
    @FXML
    private Label tuesdayTotal;
    @FXML
    private Label tuesdayEfficiency;
    @FXML
    private Label wednesdayTotal;
    @FXML
    private Label wednesdayEfficiency;
    @FXML
    private Label thursdayTotal;
    @FXML
    private Label thursdayEfficiency;
    @FXML
    private Label fridayTotal;
    @FXML
    private Label fridayEfficiency;
    @FXML
    private Label name;
    
    public void initialize(){
        //sets the name to the last user chosen in the last page(ManagerHomePage)
        name.setText(ManagerHomePageController.getNameFromChoiceBox());
        
        //if a user was not chosen on the last page, this page will not display any data
        if(!name.getText().equals("")) {
            loadData();
    
            weekChoiceBox.setOnAction(this::getWeekFromChoiceBox);
        }
    }
    
    //loads weeks into a choice box
    private void loadData(){
        weekChoiceBox.getItems().addAll("Week 1", "Week 2", "Week 3", "Week 4");
    }
    
    //takes you to the ManagerHomePage
    @FXML
    private void homePage(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/com/example/ifbproject/Views/ManagerHomePage.fxml").toURI().toURL();
    
        Parent infoParent = FXMLLoader.load(url);
        Scene infoScene = new Scene(infoParent);
    
        Stage window = (Stage) myMenuBar.getScene().getWindow();
    
        window.setScene(infoScene);
        window.show();
    }
    
    //gets the week chosen from the week choiceBox and displays the total amount of items made each day of the week and calculate the efficiency for each day.
    private void getWeekFromChoiceBox(ActionEvent actionEvent){
        String weekString = weekChoiceBox.getValue();
        int weekFromChoiceBox = 1;
        
        switch (weekString) {
            case "Week 2" -> weekFromChoiceBox = 2;
            case "Week 3" -> weekFromChoiceBox = 3;
            case "Week 4" -> weekFromChoiceBox = 4;
        }
        
        
        //sets monday's total and efficiency based on the week selected
        try {
            mondayTotal.setText(UserDayTotal.getTotal(name.getText(), DaysOfTheWeek.MONDAY, weekFromChoiceBox));
            mondayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), weekFromChoiceBox, DaysOfTheWeek.MONDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        mondayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(mondayEfficiency.getText().substring(0, mondayEfficiency.getText().length() - 1)) < 90)
            mondayEfficiency.setTextFill(Color.RED);
    
        //sets tuesday's total and efficiency based on the week selected
        try {
            tuesdayTotal.setText(UserDayTotal.getTotal(name.getText(), DaysOfTheWeek.TUESDAY, weekFromChoiceBox));
            tuesdayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), weekFromChoiceBox, DaysOfTheWeek.TUESDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        tuesdayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(tuesdayEfficiency.getText().substring(0, tuesdayEfficiency.getText().length() - 1)) < 90)
            tuesdayEfficiency.setTextFill(Color.RED);
    
        //sets wednesday's total and efficiency based on the week selected
        try {
            wednesdayTotal.setText(UserDayTotal.getTotal(name.getText(), DaysOfTheWeek.WEDNESDAY, weekFromChoiceBox));
            wednesdayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), weekFromChoiceBox, DaysOfTheWeek.WEDNESDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        wednesdayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(wednesdayEfficiency.getText().substring(0, wednesdayEfficiency.getText().length() - 1)) < 90)
            wednesdayEfficiency.setTextFill(Color.RED);
    
        //sets thursday's total and efficiency based on the week selected
        try {
            thursdayTotal.setText(UserDayTotal.getTotal(name.getText(), DaysOfTheWeek.THURSDAY, weekFromChoiceBox));
            thursdayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), weekFromChoiceBox, DaysOfTheWeek.THURSDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        thursdayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(thursdayEfficiency.getText().substring(0, thursdayEfficiency.getText().length() - 1)) < 90)
            thursdayEfficiency.setTextFill(Color.RED);
    
        //sets friday's total and efficiency based on the week selected
        try {
            fridayTotal.setText(UserDayTotal.getTotal(name.getText(), DaysOfTheWeek.FRIDAY, weekFromChoiceBox));
            fridayEfficiency.setText(EfficiencyCalculator.dayEfficiencyCalculation(name.getText(), weekFromChoiceBox, DaysOfTheWeek.FRIDAY.getDay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sets the color to green if efficiency is above 90% and red otherwise
        fridayEfficiency.setTextFill(Color.GREEN);
        if(Double.parseDouble(fridayEfficiency.getText().substring(0, fridayEfficiency.getText().length() - 1)) < 90)
            fridayEfficiency.setTextFill(Color.RED);
    }
}