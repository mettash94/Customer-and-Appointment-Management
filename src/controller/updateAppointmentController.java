package controller;

import DataAccessControl.*;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * controller for update appointment form
 */

public class updateAppointmentController implements Initializable {


    public DatePicker datePicker;
    public TextField inputStartTime;
    public TextField inputEndTime;

    public TextField inputAppointmentID;
    public TextField inputTitle;
    public TextField inputDescription;
    public TextField inputLocation;



    public ComboBox typeComboBox;
    public ComboBox customerComboBox;
    public ComboBox userComboBox;
    public ComboBox contactComboBox;

    /**
     * form is intialized with data from another page and loaded into the form
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        }

    /**
     * This just gets the selected appointment
     * -Lambda1 The for each method written as a lambda expression gets the name of the contacts from the contacts observable and puts it in a contactsname observable list
     * @param appointment
     * @throws SQLException
     */

    public void getSelectedAppointment(appointmentModel appointment) throws SQLException {

        inputAppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        inputLocation.setText(String.valueOf(appointment.getAppointmentLocation()));
        inputTitle.setText(String.valueOf(appointment.getAppointmentTitle()));
        inputDescription.setText(String.valueOf(appointment.getAppointmentDescription()));
        LocalDate dateOfAppointment=appointment.getStartDateTime().toLocalDate();

        datePicker.setValue(dateOfAppointment);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime appointmentStartTime=appointment.getStartDateTime();
        String formattedStartHour=appointmentStartTime.format(timeFormat);
        inputStartTime.setText(formattedStartHour);

        LocalDateTime appointmentEndTime=appointment.getEndDateTime();
        String formattedEndHour=appointmentEndTime.format(timeFormat);
        inputEndTime.setText(formattedEndHour);

        ObservableList<contactsModel> contactsObservableList = contactAccessControl.getContacts();
        ObservableList<String> contactNamesList=FXCollections.observableArrayList();

        // Lambda Expression 1

        //Setting contacts

        contactsObservableList.forEach(contacts -> contactNamesList.add(contacts.getContactName()));
        contactComboBox.setItems(contactNamesList);

        int contactID=appointment.getContactID();
        String contactName=null;

        for (contactsModel contact: contactsObservableList) {
            if (contact.getContactID() == contactID)
            {
                contactName=contact.getContactName();
            }
        }

        contactComboBox.setValue(contactName);


        //Setting type

        typeComboBox.setValue(appointment.getAppointmentType());


        ObservableList<String> typeList=FXCollections.observableArrayList();

        typeList.add(0,"De-Briefing");
        typeList.add(1,"Planning Session");

        typeComboBox.setItems(typeList);

         // Customer Combo box set values
        ObservableList<customerModel> customerList= customerAccessControl.getCustomers();
        customerComboBox.setItems(customerList);

        int customerID=appointment.getCustomerID();
        String customerName=null;

        for (customerModel customer: customerList) {
            if (customer.getCustomerID() == customerID)
            {
                customerName=customer.getCustomerName();
            }
        }

        customerComboBox.setValue(customerName);



        //User combo box set values
        ObservableList<userModel> userList= userAccessControl.getUsers();
        userComboBox.setItems(userList);

       int userID= appointment.getUserID();
       String userName = null;

        for (userModel user: userList) {
            if (user.getUserID() == userID) 
            {
               userName=user.getUserName();
            }
        }
        
        userComboBox.setValue(userName);

    }

    public void actionUpdateAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        String customerName=customerComboBox.getValue().toString();

        ObservableList<customerModel> customerList= customerAccessControl.getCustomers();

        int customerID = 0;

        for (customerModel customer: customerList) {
            if (customer.getCustomerName().equals(customerName))
            {
                customerID=customer.getCustomerID();
            }
        }

        // Extract User ID?
        ObservableList<userModel> userList= userAccessControl.getUsers();
        String userName=userComboBox.getValue().toString();

        int userID = 0;

        for (userModel user: userList) {
            if (user.getUserName().equals(userName))
            {
                userID=user.getUserID();
            }
        }

        //Extract contact id

        ObservableList<contactsModel> contactList= contactAccessControl.getContacts();
        String contactName=contactComboBox.getValue().toString();
        System.out.println("Contact name is" + contactName);

        int contactID = 0;

        for (contactsModel contact: contactList) {
            if (contact.getContactName().equals(contactName))
            {
                contactID=contact.getContactID();
                System.out.println(contactID);
            }
        }



        // extract appointment ID
        int appointmentID=Integer.parseInt(inputAppointmentID.getText());






        //Validate date and time

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate appointmentDate=datePicker.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(appointmentDate,
                LocalTime.parse(inputStartTime.getText(), timeFormat));

        LocalDateTime endDateTime = LocalDateTime.of(appointmentDate,
                LocalTime.parse(inputEndTime.getText(), timeFormat));

        // Validate and insert to sql

        if(checkInputFields() && checkTimeFormat() &&checkBusinessHours(startDateTime,endDateTime)
                && checkOverlap(customerID,startDateTime,endDateTime)){
            System.out.println("validated");


            String sql =  "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ? , User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, inputTitle.getText());
            ps.setString(2, inputDescription.getText());
            ps.setString(3, inputLocation.getText());
            ps.setString(4, typeComboBox.getValue().toString());
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "script");



            ps.setInt(9, customerID);

            ps.setInt(10, userID);

            ps.setInt(11, contactID);

            ps.setInt(12, appointmentID);

            ps.execute();

            Parent root = FXMLLoader.load(getClass().getResource("/view/viewAppointmentScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();



        }


    }

    public  Boolean checkInputFields(){
        if (
                !inputTitle.getText().isEmpty() && !inputDescription.getText().isEmpty()
                        && !inputLocation.getText().isEmpty()
                        && customerComboBox.getValue() !=null
                        && userComboBox.getValue() !=null
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

    /**
     * method checks overlap of appointments
     */

    public Boolean checkOverlap(int customerID, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime
    ) throws SQLException {

        ObservableList<appointmentModel> appointmentObservableList= appointmentAccessControl.getAppointments();

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
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer's Appointment overlaps with existing appointment.");

                alert.showAndWait();
                return false;

            }

        }
        return true;
    }





























    /**
     * cancels update and returns to appointment screen
     * @param actionEvent
     * @throws IOException
     */
    public void actionCancelUpdate(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/viewAppointmentScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
