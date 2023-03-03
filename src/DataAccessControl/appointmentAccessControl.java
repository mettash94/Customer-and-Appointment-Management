package DataAccessControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointmentModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import helper.JDBC;

/**
 * Gets sql data on appointments and puts it in an observable list
 */

public class appointmentAccessControl {
    public static ObservableList<appointmentModel> getAppointments() throws SQLException {

        ObservableList<appointmentModel> appointmentsObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * from appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");

            //for DBMS operations use TimeStamp

            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();

            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();


            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            appointmentModel appointment = new appointmentModel(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);


        }

        return appointmentsObservableList;
        /**
         * Gets sql data on months appointments and puts it in an observable list
         */
    }
    public static ObservableList<appointmentModel> getThisMonthsAppointments() throws SQLException {

        ObservableList<appointmentModel> appointmentsObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * from appointments WHERE month(start)=month(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");

            //for DBMS operations use TimeStamp

            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();

            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();


            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            appointmentModel appointment = new appointmentModel(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);


        }

        return appointmentsObservableList;
    }

    /**
     * Gets sql data on appointments by week and puts it in an observable list
     */
    public static ObservableList<appointmentModel> getThisWeeksAppointments() throws SQLException {

        ObservableList<appointmentModel> appointmentsObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * from appointments WHERE yearweek(start)=yearweek(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");

            //for DBMS operations use TimeStamp

            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();

            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();


            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            appointmentModel appointment = new appointmentModel(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);


        }

        return appointmentsObservableList;
    }



}