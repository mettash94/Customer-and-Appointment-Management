package controller;

import DataAccessControl.appointmentAccessControl;
import DataAccessControl.contactAccessControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointmentModel;
import model.contactsModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * contoller for showing reports for each contact when selected
 */

public class reportsContactsScheduleController {
    public TableView allAppointmentsTable;
    public TableColumn appointmentIDColumn;
    public TableColumn appointmentTitleColumn;
    public TableColumn appointmentDescriptionColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public TableColumn appointmentEndColumn;
    public TableColumn CustomerIDColumn;
    public ComboBox<contactsModel>selectContactComboBox;

    public void initialize() throws SQLException {

        ObservableList<contactsModel> contactsObservableList = contactAccessControl.getContacts();

        selectContactComboBox.setItems(contactsObservableList);



    }
    /**
     * action when contact is selected it shows the reports speciifc to the contact
     */

    /**
     * Lambda expression2
     * Filters appointment based on contactID
     * @param actionEvent
     * @throws SQLException
     */
    public void actionContactSelected(ActionEvent actionEvent) throws SQLException {


        contactsModel selectedContactName = (contactsModel) selectContactComboBox.getSelectionModel().getSelectedItem();

        int selectedContactID=selectedContactName.getContactID();



        ObservableList<appointmentModel> allAppointmentsList = appointmentAccessControl.getAppointments();
        ObservableList<appointmentModel> appointmentsOfTheContactList=allAppointmentsList.filtered(appointment -> {
            if (appointment.getContactID() == selectedContactID) {
               return true;
            }
            return false;
        });


        allAppointmentsTable.setItems(appointmentsOfTheContactList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));



    }
    /**
     * action back to main menu
     */

    public void actionBackToReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reportsScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
