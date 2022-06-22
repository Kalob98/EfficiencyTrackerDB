package com.example.ifbproject.Utils;

import com.example.ifbproject.Database.DatabaseInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/14/2022
 *
 * This class is populating John's production ArrayList with random numbers that represent the amount of items he made
 * each day of the month, except last day because the counter is counting the last day's amount.
 *
 * Each month will have 4weeks consisting of 5 days.
 */

public class MonthlyQuota {
    //sets limits for daily quota amounts
    private static final int UPPERLIMIT = 600;
    private static final int LOWERLIMIT = 300;
    
    //generating random data for everyday of the month except the last day
    public static void fillDatabase() throws SQLException {
        //getting all the userIds from the database
        ArrayList<UUID> userIds = new ArrayList<>();
        String selectAllUserNames = "SELECT \"id\" FROM users where \"isManager\" = false";
        PreparedStatement preparedStatementUserNames = DatabaseInfo.getConnection().prepareStatement(selectAllUserNames);
        ResultSet resultSet = preparedStatementUserNames.executeQuery();
        while(resultSet.next()){
            userIds.add((UUID) resultSet.getObject("id"));
        }
        
        //creating sql statement to insert data into the database
        String insertIntoProduction = "INSERT INTO production VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatementInsert = DatabaseInfo.getConnection().prepareStatement(insertIntoProduction);
        
        for (int i = 1; i <= 20; i++) {
            for (UUID id : userIds) {
                preparedStatementInsert.setInt(1, i);
                
                preparedStatementInsert.setObject(3, id);
                
                if(i == 20){
                    preparedStatementInsert.setInt(4, 0);
                }
                else {
                    preparedStatementInsert.setInt(4, (int) (Math.random() * ((UPPERLIMIT - LOWERLIMIT) + 1) + LOWERLIMIT));
                }
                
                switch (i % 5) {
                    case 0 -> preparedStatementInsert.setString(2, DaysOfTheWeek.FRIDAY.toString());
                    case 1 -> preparedStatementInsert.setString(2, DaysOfTheWeek.MONDAY.toString());
                    case 2 -> preparedStatementInsert.setString(2, DaysOfTheWeek.TUESDAY.toString());
                    case 3 -> preparedStatementInsert.setString(2, DaysOfTheWeek.WEDNESDAY.toString());
                    case 4 -> preparedStatementInsert.setString(2, DaysOfTheWeek.THURSDAY.toString());
                }
                preparedStatementInsert.execute();
            }
        }
    }
}