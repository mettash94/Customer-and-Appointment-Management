package controller;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import DataAccessControl.customerAccessControl;
import DataAccessControl.appointmentAccessControl;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointmentModel;
import model.customerModel;

/**
 * controller for viewing all customers table and its attributes
 */

public class customerController implements Initializable {

    public TableView<customerModel> customerRecordsTable;
    public TableColumn<Object, Object> customerColumnId;
    public TableColumn<Object, Object> customerColumnName;
    public TableColumn<Object, Object> customerColumnAddress;
    public TableColumn<Object, Object> customerColumnPostalCode;
    public TableColumn<Object, Object> customerColumnPhone;
    public TableColumn<Object, Object> customerColumnDivisionID;
    public TableColumn<Object, Object> customerColumnDivisionName;
    public Button customerAddButton;
    public Button customerUpdateButton;
    public Button customerDeleteButton;
    public Button customerExitButton;

    /**
     *
     * initializes the table view for customer with data
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        try {
            JDBC.openConnection();
            ObservableList<customerModel> allCustomersList = customerAccessControl.getCustomers();

            customerRecordsTable.setItems(allCustomersList);
            customerColumnId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerColumnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerColumnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerColumnPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerColumnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerColumnDivisionID.setCellValueFactory(new PropertyValueFactory<>("CustomerDivisionID"));
            customerColumnDivisionName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * action return to main menu
     */

    public void actionReturnToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * action add customer goes to add form
     */

    public void actionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * action update takes data of the selected customer to update and goes to update form screen
     */

    public void actionCustomerUpdate(ActionEvent actionEvent) throws IOException, SQLException {


        customerModel selectedCustomerToUpdate = customerRecordsTable.getSelectionModel().getSelectedItem();





        System.out.println(selectedCustomerToUpdate);


        if (selectedCustomerToUpdate == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Please Select a Customer to be updated");
            alertError.showAndWait();

        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
            loader.load();
            updateCustomerController UCC = loader.getController();
            UCC.getSelectedProduct((customerModel) customerRecordsTable.getSelectionModel().getSelectedItem());


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        }





    }

    /**
     * action delete first deletes appointment of the customer and then all the appointments
     * and updates the table view
     */

    public void actionDeleteCustomer(ActionEvent actionEvent) throws SQLException {

        customerModel selectedCustomerToDelete = customerRecordsTable.getSelectionModel().getSelectedItem();

        int customerID=selectedCustomerToDelete.getCustomerID();



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Customer and their appointments? ");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK)

        {

            String sql = "DELETE FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);

            ps.execute();
            ps.executeUpdate();
            ps.close();


            String sql2 = "DELETE FROM customers WHERE Customer_ID=?";
            PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
            ps2.setInt(1, customerID);

            ps2.execute();
            ps2.executeUpdate();
            ps2.close();

            ObservableList<customerModel> allCustomersList = customerAccessControl.getCustomers();

            customerRecordsTable.setItems(allCustomersList);

        }



    }
}
