package com.example.ifbproject.Controllers;

import com.example.ifbproject.Database.DatabaseInfo;
import com.example.ifbproject.Model.UserUUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/15/2022
 *
 * Controller Class for the CounterPage. Adds the buttons that increase and decrease the counter and saves that number
 */

public class CounterPageController {
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private Label name;
    @FXML
    private Label counter;
    
    //Number used to keep track of the number the counter is currently at
    private static int count = 0;
    //Number that changes the amount the counter goes up or down
    private final int numberToIncOrDec = 100;
    //Gets the user's name
    private final String userName = LoginPageController.getName();
    
    //Displays the user's name and sets the counter
    public void initialize(){
        name.setText(userName);
        counter.setText(Integer.toString(count));
    }
    
    //button to add to counter
    @FXML
    private void addToTotal(ActionEvent actionEvent) {
        int number = Integer.parseInt(counter.getText());
        counter.setText(Integer.toString(number + numberToIncOrDec));
    }
    
    //button to decrease counter
    @FXML
    private void subToTotal(ActionEvent actionEvent) {
        int number = Integer.parseInt(counter.getText());
        
        //doesn't let the counter go below 0
        if(number > 0) counter.setText(Integer.toString(number - numberToIncOrDec));
    }
    
    //Takes you to the efficiency page
    @FXML
    private void efficiencyPage(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/com/example/ifbproject/Views/EfficiencyPage.fxml").toURI().toURL();
        
        Parent infoParent = FXMLLoader.load(url);
        Scene infoScene = new Scene(infoParent);
        
        Stage window = (Stage) myMenuBar.getScene().getWindow();
        
        window.setScene(infoScene);
        window.show();
    }
    
    //Button to save the count
    @FXML
    private void saveCount(ActionEvent actionEvent) throws SQLException {
        //Saving the current count to the database
        String saveCountToDatabase = "update production set \"itemsCreated\" = ? where \"dayOfMonth\" = 20 and \"dayName\" = 'FRIDAY' and \"userId\" = ?";
        PreparedStatement preparedStatementSaveCount = DatabaseInfo.getConnection().prepareStatement(saveCountToDatabase);
        preparedStatementSaveCount.setInt(1, Integer.parseInt(counter.getText()));
        preparedStatementSaveCount.setObject(2, UserUUID.getUUID(userName));
        preparedStatementSaveCount.execute();
        
        //Getting the count from the database
        String getCountFromDatabase = "select \"itemsCreated\" from production where \"dayOfMonth\" = 20 and \"dayName\" = 'FRIDAY' and \"userId\" = ?";
        PreparedStatement preparedStatementUpdateCount = DatabaseInfo.getConnection().prepareStatement(getCountFromDatabase);
        preparedStatementUpdateCount.setObject(1, UserUUID.getUUID(userName));
        ResultSet resultSetCount = preparedStatementUpdateCount.executeQuery();
        
        //updates count
        if(resultSetCount.next())
            count = resultSetCount.getInt("itemsCreated");
    }
}