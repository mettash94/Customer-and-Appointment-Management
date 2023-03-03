package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;


/** Appointment Scheduling application
 *
 * JAVADOC folder location C195/javadoc
 *
 * @author Shwetha Mettakadapa
 * */

public class Main extends Application {
    @Override

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginScreen.fxml"));
        stage.setTitle("Appointment Scheduler, Customers and Reports");
        stage.setScene(new Scene(root));
        stage.show();
    }



    public static void main(String[] args) throws Exception {

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();

    }

}