package controller;

import DataAccessControl.*;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.TimeZone;

/**
 * controller class for adding appointments
 */

public class addAppointmentController {
    public TextField inputAppointmentID;
    public TextField inputTitle;
    public TextField inputDescription;
    public TextField inputLocation;




    public ComboBox<contactsModel> contactComboBox;




    public ComboBox<String> typeComboBox;

    public ComboBox<customerModel> customerNameComboBox;
    public ComboBox<userModel> userNameComboBox;

    public DatePicker datePicker;
    public TextField inputStartTime;
    public TextField inputEndTime;
    /**
     * initializes the table view showing all appointments and the attributes
     */
    public void initialize() throws SQLException {


        //Extract contact name

        ObservableList<contactsModel> contactsObservableList = contactAccessControl.getContacts();
        contactComboBox.setItems(contactsObservableList);


        // Initialize type combo-box;

        ObservableList<String> typeList=FXCollections.observableArrayList();

        typeList.add(0,"De-Briefing");
        typeList.add(1,"Planning Session");

        typeComboBox.setItems(typeList);

        //Initialize CustomerId combo box. Extract customer ID how?

        ObservableList<customerModel> customerList=customerAccessControl.getCustomers();
        customerNameComboBox.setItems(customerList);

        //Initialize User ID combo box. Extract User ID?
        ObservableList<userModel> userList= userAccessControl.getUsers();
        userNameComboBox.setItems(userList);








    }

    /**
     * adds an appointment
     */

    public void actionAddAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        JDBC.openConnection();



        int customerID=customerNameComboBox.getValue().getCustomerID();

        //Validate date and time

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate appointmentDate=datePicker.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(appointmentDate,
               LocalTime.parse(inputStartTime.getText(), timeFormat));

        LocalDateTime endDateTime = LocalDateTime.of(appointmentDate,
                LocalTime.parse(inputEndTime.getText(), timeFormat));





        // Validate and insert to sql

        if(checkInputFields() && checkTimeFormat() &&checkBusinessHours(startDateTime,endDateTime)
                && checkOverlap(customerID,startDateTime,endDateTime))

        {


            System.out.println("validated");

            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?,?)";


            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ps.setString(1, inputTitle.getText());
            ps.setString(2, inputDescription.getText());

            ps.setString(3, inputLocation.getText());

            ps.setString(4, typeComboBox.getValue());

            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));

            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

            ps.setString(8, "script");

            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

            ps.setString(10, "script");

            ps.setInt(11, customerNameComboBox.getValue().getCustomerID());
            ps.setInt(12, userNameComboBox.getValue().getUserID());

            ps.setInt(13, contactComboBox.getValue().getContactID());


            ps.execute();

            Parent root = FXMLLoader.load(getClass().getResource("/view/viewAppointmentScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        }


        }

    /**
     * method checks overlap of appointments
     */

        public Boolean checkOverlap(int customerID, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime
                                   ) throws SQLException {

            ObservableList<appointmentModel> appointmentObservableList=appointmentAccessControl.getAppointments();

            for (appointmentModel appointment: appointmentObservableList)
            {
                if (customerID != appointment.getCustomerID())
                {
                    continue;
                }
                LocalDateTime existingAppointmentStart = appointment.getStartDateTime();
                LocalDateTime existingAppointmentEnd = appointment.getEndDateTime();


                if (

                        (appointmentStartTime.isAfter(existingAppointmentStart) && appointmentStartTime.isBefore(existingAppointmentEnd))||
                                (appointmentEndTime.isAfter(existingAppointmentStart) && appointmentEndTime.isBefore(existingAppointmentEnd))
                    || (appointmentStartTime.isBefore(existingAppointmentStart) && appointmentEndTime.isAfter(existingAppointmentEnd))
                    || (appointmentStartTime.isEqual(existingAppointmentStart))
                    ||(appointmentEndTime.isEqual(existingAppointmentEnd))


                )
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment.");

                    alert.showAndWait();
                    return false;

                }

            }
            return true;
        }

    /**
     * method checks input fields and returns a boolean
     */

    public  Boolean checkInputFields(){
        if (
                !inputTitle.getText().isEmpty() && !inputDescription.getText().isEmpty()
                        && !inputLocation.getText().isEmpty()
                        && customerNameComboBox.getValue() !=null
                        && userNameComboBox.getValue() !=null
                        && typeComboBox.getValue() !=null
                        && datePicker.getValue() != null
                        && !inputStartTime.getText().isEmpty()
                        && !inputEndTime.getText().isEmpty()

            )
            return true;
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check all the input fields are valid and selected");
                alert.showAndWait();
                return false;
            }

        }

        public Boolean checkTimeFormat(){

        try {

            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

            LocalDateTime startDateTime = LocalDateTime.of(datePicker.getValue(),
                    LocalTime.parse(inputStartTime.getText(), timeFormat));

            return true;
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter format HH:MM");
            alert.showAndWait();
            return false;
        }
        }

    /**
     * method checks business hours and returns a boolean
     */

    public Boolean checkBusinessHours(LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime){

       //Get Zone lets say PT
        ZoneId zone = ZoneId.systemDefault();

        //Get ZonedTime of appointment in PT
        ZonedDateTime appointmentStartTimeInZone=ZonedDateTime.of(appointmentStartTime,zone);
        ZonedDateTime appointmentEndTimeInZone=ZonedDateTime.of(appointmentEndTime,zone);

        //How do I convert PT time  to EST time to compare?
        ZonedDateTime appointmentStartTimeInEST=appointmentStartTimeInZone.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime appointmentEndTimeInEST=appointmentEndTimeInZone.withZoneSameInstant(ZoneId.of("America/New_York"));


        //Get Zoned Time of business hours in EST
        ZonedDateTime businessStartHourEST = ZonedDateTime.of(appointmentStartTime.toLocalDate(), LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime businessEndHourEST = ZonedDateTime.of(appointmentEndTime.toLocalDate(), LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));


        //Used for alert
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        String formattedStartHour=businessStartHourEST.format(timeFormat);
        String formattedEndHour=businessEndHourEST.format(timeFormat);


        if ((businessStartHourEST.isBefore(appointmentStartTimeInEST) || businessStartHourEST.isEqual(appointmentStartTimeInEST))
                && ((businessEndHourEST.isAfter(appointmentEndTimeInEST)|| businessEndHourEST.isEqual(appointmentEndTimeInEST)))



        )
        {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Book between "  +  formattedStartHour+ " AM " + formattedEndHour + " PM");
            alert.showAndWait();
            System.out.println(formattedStartHour);
            return false;
        }


    }




    }






