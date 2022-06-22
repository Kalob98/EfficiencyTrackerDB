package com.example.ifbproject.Model;

import com.example.ifbproject.Database.DatabaseInfo;
import com.example.ifbproject.Utils.DaysOfTheWeek;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/22/2022
 *
 * Class used to get the total amount of items created from the database
 */

public class UserDayTotal {
    public static String getTotal(String userName, DaysOfTheWeek day, int week) throws SQLException {
        int getToTheEndOfTheWeek = 5;
        int total = 0;
        
        //sql to get itemsCreated from the db
        String getTotalFromDB = "SELECT \"itemsCreated\" from production where \"userId\" = ? and \"dayName\" = ? and \"dayOfMonth\" = ?";
        PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(getTotalFromDB);
        preparedStatement.setObject(1, UserUUID.getUUID(userName));
        preparedStatement.setString(2, day.toString());
        preparedStatement.setInt(3, (week * getToTheEndOfTheWeek) - (getToTheEndOfTheWeek - day.getDay()));
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next())
            total = resultSet.getInt("itemsCreated");

        return String.valueOf(total);
    }
}