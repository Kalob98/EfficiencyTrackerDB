package com.example.ifbproject.Model;

import com.example.ifbproject.Database.DatabaseInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/17/2022
 *
 * Class used to calculate the day, week, and month efficiency
 */

public class EfficiencyCalculator {
    //Formatter used to format the efficiency
    private static final NumberFormat FORMATTER = new DecimalFormat("#0.00");
    
    //daily quota used to determine efficiency
    private static final double QUOTA = 500.00;
    private static final int numberOfDaysInWeek = 5;
    private static final int numberOfDaysInMonth = 20;
    private static final int getToTheStartOfTheWeek = 4;
    private static final int getToTheEndOfTheWeek = 5;
    
    //calculating the users' day efficiency
    public static String dayEfficiencyCalculation(String name, int week, int day) throws SQLException {
        int dayTotal = 0;
        
        String getDayTotal = "select \"itemsCreated\" from production where \"userId\" = ? and \"dayOfMonth\" = ?";
        PreparedStatement preparedStatementSaveCount = DatabaseInfo.getConnection().prepareStatement(getDayTotal);
        preparedStatementSaveCount.setObject(1, UserUUID.getUUID(name));
        preparedStatementSaveCount.setInt(2, (week * day));
        ResultSet resultSet = preparedStatementSaveCount.executeQuery();
        if(resultSet.next()){
            dayTotal = resultSet.getInt("itemsCreated");
        }
        
        //100.0 is used to get a percentage
        return FORMATTER.format((dayTotal / QUOTA) * 100.00) + "%";
    }
    
    //calculating the users' week efficiency based on the week provided
    public static String weekEfficiencyCalculation(String name, int weekNumber) throws SQLException {
        String getDayTotal = "select \"itemsCreated\" from production where \"userId\" = ? and \"dayOfMonth\" = ?";
        PreparedStatement preparedStatementSaveCount = DatabaseInfo.getConnection().prepareStatement(getDayTotal);
        
        int sum = 0;
        
        //loop used to add all the week's items made by the user
        for(int i = (weekNumber * getToTheEndOfTheWeek) - getToTheStartOfTheWeek; i <= (weekNumber * getToTheEndOfTheWeek); i++){
            preparedStatementSaveCount.setObject(1, UserUUID.getUUID(name));
            preparedStatementSaveCount.setInt(2, i);
            ResultSet resultSet = preparedStatementSaveCount.executeQuery();
            if(resultSet.next()){
                sum += resultSet.getInt("itemsCreated");
            }
        }
        
        //100.0 is used to get a percentage
        return FORMATTER.format(((sum / numberOfDaysInWeek) / QUOTA) * 100.0) + "%";
    }
    
    //calculating the users' month efficiency
    public static String monthEfficiencyCalculation(String name) throws SQLException {
        String getDayTotal = "select \"itemsCreated\" from production where \"userId\" = ? and \"dayOfMonth\" = ?";
        PreparedStatement preparedStatementSaveCount = DatabaseInfo.getConnection().prepareStatement(getDayTotal);
        preparedStatementSaveCount.setObject(1, UserUUID.getUUID(name));
        
        int sum = 0;
        
        //loop used to add all the month's items made by the user
        for(int i = 1; i <= 20; i++){
            preparedStatementSaveCount.setInt(2, i);
            ResultSet resultSet = preparedStatementSaveCount.executeQuery();
            if(resultSet.next()){
                sum += resultSet.getInt("itemsCreated");
            }
        }
    
        //100.0 is used to get a percentage
        return FORMATTER.format(((sum / numberOfDaysInMonth) / QUOTA) * 100.0) + "%";
    }
}