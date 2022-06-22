package com.example.ifbproject.Model;

import com.example.ifbproject.Database.DatabaseInfo;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/21/2022
 *
 * Class to check if the user entered the correct password
 */

public class CheckPassword {
    public static Boolean isPasswordCorrect(String name, String password) throws SQLException {
        String checkUserPassword = "SELECT \"password\" from users where \"name\" = ?";
        String userPassword;
        
        PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(checkUserPassword);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            userPassword = resultSet.getString("password");
            if(!userPassword.equals(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password is incorrect, please try again.");
                alert.show();
            }
            else
                return true;
        }
        
        return false;
    }
}