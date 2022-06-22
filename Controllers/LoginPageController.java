package com.example.ifbproject.Controllers;

import com.example.ifbproject.Model.CheckPassword;
import com.example.ifbproject.Database.DatabaseInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
 * Last edited: 6/13/2022
 *
 * LoginPage Controller. Where a user will pick their name from a choice box or a manager will choose manager from the
 * choice box
 */

public class LoginPageController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;
    @FXML
    private ChoiceBox<String> usersChoiceBox;
    
    //Gets the users name
    private static String name = "";
    
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
        
        loadData(userNames);
    
        //sets name to username selected in choiceBox
        usersChoiceBox.setOnAction(this::getUsersName);
    }
    
    //loads the usernames and manager into the choiceBox
    private void loadData(ArrayList<String> userNames){
        usersChoiceBox.getItems().addAll(userNames);
    }
    
    //When the login button is clicked
    @FXML
    private void login(ActionEvent actionEvent) throws IOException, SQLException {
        //prints error message to the user if a user is not selected
        if(name.equals(""))
            errorMessage.setText("Please select a user from the drop down menu below.");
        
        //takes employees to their page(s)
        if(!name.equals("Manager") && !name.equals("")) {
            //checks password
            if(CheckPassword.isPasswordCorrect(name, passwordField.getText())){
                URL url = new File("src/main/java/com/example/ifbproject/Views/CounterPage.fxml").toURI().toURL();
                
                Parent infoParent = FXMLLoader.load(url);
                Scene infoScene = new Scene(infoParent);
        
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        
                window.setScene(infoScene);
                window.show();
            }
        }
        //takes managers to their page(s)
        if(name.equals("Manager")) {
            //checks password
            if(CheckPassword.isPasswordCorrect(name, passwordField.getText())) {
                URL url = new File("src/main/java/com/example/ifbproject/Views/ManagerHomePage.fxml").toURI().toURL();
    
                Parent infoParent = FXMLLoader.load(url);
                Scene infoScene = new Scene(infoParent);
    
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    
                window.setScene(infoScene);
                window.show();
            }
        }
    }
    
    private void getUsersName(ActionEvent actionEvent) {
        name = usersChoiceBox.getValue();
    }
    
    // ############# Getter #############
    public static String getName() {
        return name;
    }
}