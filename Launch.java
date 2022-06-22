package com.example.ifbproject;

import com.example.ifbproject.Database.DatabaseInfo;
import com.example.ifbproject.Utils.MonthlyQuota;
import com.example.ifbproject.Utils.UserNames;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Launch extends Application {
    
    //starts the application
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //check if database is empty, if yes call insertUserNames() and fillDatabase()
        String select = "select * from users";
        PreparedStatement preparedStatement = DatabaseInfo.getConnection().prepareStatement(select);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()) {
            UserNames.insertUserNames();
            MonthlyQuota.fillDatabase();
            System.out.println("added dummy data");
        }
        else {
            System.out.println("didn't add dummy data");
        }
        
        //replaces day 20 with zeros
        String resetLastDayToZero = "UPDATE production set \"itemsCreated\" = 0 where \"dayOfMonth\" = 20 and \"dayName\" = 'FRIDAY'";
        PreparedStatement preparedStatementZero = DatabaseInfo.getConnection().prepareStatement(resetLastDayToZero);
        preparedStatementZero.execute();
        System.out.println("Replaced last day with 0's");
        
        URL url = new File("src/main/java/com/example/ifbproject/Views/LoginPage.fxml").toURI().toURL();
        
        Scene scene = new Scene(FXMLLoader.load(url), 600, 400);
        stage.setTitle("IFB Project");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}