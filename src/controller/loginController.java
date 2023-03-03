package controller;

import helper.LogSessions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import DataAccessControl.userAccessControl;

import DataAccessControl.appointmentAccessControl;
import model.appointmentModel;

/**
 * controller for login page
 */

public class loginController implements Initializable {
    public TextField inputUserName;
    public TextField inputPassword;
    @FXML
    private Label loginFormLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label passwordLabel;



    ResourceBundle rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

    @FXML
    private Label zoneIdLabel;

    /**
     * initializes the login page with zone name and computers language settings
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();

        zoneIdLabel.setText(String.valueOf(zone));


        rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

        userIdLabel.setText(rb.getString("username"));

        passwordLabel.setText(rb.getString("password"));

        loginButton.setText(rb.getString("login"));

        exitButton.setText(rb.getString("exit"));

        loginFormLabel.setText(rb.getString("loginForm"));



    }
    /**
     * action login checks inputs using validaiton methods and goes to main screen
     */

    @FXML
    public void actionBtnLogin(ActionEvent actionEvent) throws IOException, SQLException {




        if(!inputUserName.getText().isEmpty() && !inputPassword.getText().isEmpty()) {

            String username = inputUserName.getText();
            String password = inputPassword.getText();

            //Using the user validation method get the boolean value and use the record loginattempt method and log if the attempt was success or failure

            boolean logonSuccessOrFail = userAccessControl.userValidation(username,password);

            System.out.println(logonSuccessOrFail);

            LogSessions.recordLoginAttempt(username,logonSuccessOrFail);




            if (userAccessControl.userValidation(username, password) ) {





                checkAppointments(username,password);



                Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("invalid"));



                Optional<ButtonType> confirmation = alert.showAndWait();

            }

        }

        else {
            rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("empty"));

            Optional<ButtonType> confirmation = alert.showAndWait();

        }

    }
    /**
     * checks appointments based to the user and displays alerts
     */

    public void checkAppointments(String username, String password) throws SQLException {
        ObservableList<appointmentModel> appointmentsList= appointmentAccessControl.getAppointments();

        int userID=userAccessControl.getUserID(username,password);

        LocalDateTime appointmentIn15Min = LocalDateTime.now().plusMinutes(15);
        LocalDateTime timeNow=LocalDateTime.now();

        System.out.println(appointmentIn15Min);

        boolean found=false;

        for (appointmentModel appointment: appointmentsList) {
            if (appointment.getUserID() == userID) {
                if (
                        (appointment.getStartDateTime().isAfter(timeNow) && appointment.getStartDateTime().isBefore(appointmentIn15Min))
                )
                {
                     found=true;
                    System.out.println(appointment.getStartDateTime());

                    rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("appointmentsoon")
                            + appointment.getAppointmentID() + " on " + appointment.getStartDateTime().toLocalDate() +
                            " at " + appointment.getStartDateTime());
                    Optional<ButtonType> confirmation = alert.showAndWait();

                }


            }

        }
        if(!found) {

            rb = ResourceBundle.getBundle("languageProperties/login", Locale.getDefault());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("noappointment"));
            Optional<ButtonType> confirmation = alert.showAndWait();
        }


        }



    }




