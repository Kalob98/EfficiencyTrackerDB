package com.example.ifbproject.Model;

import com.example.ifbproject.Controllers.LoginPageController;
import com.example.ifbproject.Database.DatabaseInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/22/2022
 *
 * Returns a users UUID
 */

public class UserUUID {
    public static UUID getUUID(String userName) throws SQLException {
        UUID uuid = null;
        String getUUIDFromDB = "SELECT \"id\" from users where \"name\" = ?";
        PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(getUUIDFromDB);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if(resultSet.next())
            uuid = (UUID) resultSet.getObject("id");
        return uuid;
    }
}