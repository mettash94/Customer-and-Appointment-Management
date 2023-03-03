package controller;

import DataAccessControl.appointmentAccessControl;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointmentModel;
import model.customerModel;
import model.divisionModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * controller to view all appointments
 */
public class viewAppointmentsController {
    public TableColumn appointmentIDColumn;
    public TableColumn appointmentTitleColumn;

    public TableColumn appointmentDescriptionColumn;
    public TableColumn appointmentLocationColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public TableColumn appointmentEndColumn;
    public TableColumn appointmentCustomerIDColumn;
    public TableColumn contactIDColumn;
    public TableColumn userIDColumn;

    public TableView<appointmentModel> allAppointmentsTable;
    public Button backToMainMenu;
    public TableColumn appointmentID;

    /**
     * data is initialized from obseravable list
     * @throws SQLException
     */
    public void initialize() throws SQLException {


        ObservableList<appointmentModel> allAppointmentsList = appointmentAccessControl.getAppointments();

        allAppointmentsTable.setItems(allAppointmentsList);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));

        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }

    /**
     * back to main menu
     * @param actionEvent
     * @throws IOException
     */

    public void actionBackToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * when week radio button is selected shows appointments for that week
     * @param actionEvent
     * @throws SQLException
     */
    public void appointmentsSelectedByWeek(ActionEvent actionEvent) throws SQLException {


        ObservableList<appointmentModel> appointmentsByWeekList = appointmentAccessControl.getThisWeeksAppointments();



        allAppointmentsTable.setItems(appointmentsByWeekList);


    }

    /**
     * month is selected and that months appointments are shown
     * @param actionEvent
     * @throws SQLException
     */


    public void appointmentsSelectedByMonth(ActionEvent actionEvent) throws SQLException {

        ObservableList<appointmentModel> thisMonthsAppointmentsList = appointmentAccessControl.getThisMonthsAppointments();


        allAppointmentsTable.setItems(thisMonthsAppointmentsList);
    }


    /**
     * all appointment radio button is selected
     * @param actionEvent
     * @throws SQLException
     */

    public void allAppointmentsSelected(ActionEvent actionEvent) throws SQLException {


        ObservableList<appointmentModel> allAppointmentsList = appointmentAccessControl.getAppointments();
        allAppointmentsTable.setItems(allAppointmentsList);




    }

    /**
     * add appointment
     * @param actionEvent
     * @throws IOException
     */

    public void actionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointmentScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * update eappointments
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */

    public void actionUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException{
        appointmentModel selectedAppointmentToUpdate = allAppointmentsTable.getSelectionModel().getSelectedItem();

        System.out.println(selectedAppointmentToUpdate);


        if (selectedAppointmentToUpdate == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Please Select an Appointment to be updated");
            alertError.showAndWait();

        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateAppointment.fxml"));
            loader.load();
            updateAppointmentController UAC = loader.getController();
            UAC.getSelectedAppointment((appointmentModel) allAppointmentsTable.getSelectionModel().getSelectedItem());


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        }

    }

    /**
     * delete appointments
     * @param actionEvent
     * @throws SQLException
     */

    public void actionDeleteSelectedAppointment(ActionEvent actionEvent) throws SQLException {

        appointmentModel selectedAppointmentToDelete = allAppointmentsTable.getSelectionModel().getSelectedItem();

        int customerID=selectedAppointmentToDelete.getCustomerID();
        int appointmentID=selectedAppointmentToDelete.getAppointmentID();

        String appointmentType=selectedAppointmentToDelete.getAppointmentType();

        System.out.println(appointmentID);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Delete appointment with ID " + appointmentID + " of type " + appointmentType + " ?");

        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK)
        {

            System.out.println(selectedAppointmentToDelete);

            String sql = "DELETE FROM appointments WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);

            ps.execute();
            ps.executeUpdate();
            ps.close();

        }



        ObservableList<appointmentModel> AppointmentsList = appointmentAccessControl.getAppointments();
        allAppointmentsTable.setItems(AppointmentsList);

    }
}

