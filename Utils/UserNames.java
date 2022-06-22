package com.example.ifbproject.Utils;

import com.example.ifbproject.Database.DatabaseInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/13/2022
 *
 * ArrayList of the users that is used to load username choiceBoxes
 */

public class UserNames {
    
    public static void insertUserNames() throws SQLException {
        String insertIntoDatabase = "INSERT INTO users VALUES (?, ?, ?, ?)";
    
        ArrayList<String> userNames = new ArrayList<>();
        userNames.add("John");
        userNames.add("Ava");
        userNames.add("Carl");
        userNames.add("Sophia");
        userNames.add("Manager");
        
        for(String name : userNames){
            PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(insertIntoDatabase);
            preparedStatement.setObject(1, UUID.randomUUID());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, name);
            preparedStatement.setBoolean(4, false);
            if(name.equals("Manager")){
                preparedStatement.setBoolean(4, true);
            }
            preparedStatement.execute();
        }
    }
    
    //INSERT INTO "User" ("id", "name", "password")
    //VALUES (UUID.randomUUID(), 'John', 'temp')
    
}