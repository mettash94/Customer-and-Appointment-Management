package controller;

import DataAccessControl.reportsAccessControl;
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
import model.customerModel;
import model.customerTotalAppointmentsModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * controller for my report showing customer name and frequency of their total appointments
 */

public class myReportController {


    public TableView customerListTable;
    public TableColumn customerNameColumn;
    public TableColumn totalAppointmentsColumn;

    public void initialize() throws SQLException {

        ObservableList<customerTotalAppointmentsModel> customerAndAppointmentsTotalList = reportsAccessControl.getCustomerAndAppointmentsTotal();

        System.out.println(customerAndAppointmentsTotalList);
        customerListTable.setItems(customerAndAppointmentsTotalList);

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        totalAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));



    }


    public void actionBackToReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reportsScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
