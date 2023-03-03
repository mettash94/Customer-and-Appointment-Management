package controller;

import DataAccessControl.countryAccessControl;
import DataAccessControl.divisionAccessControl;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.countryModel;
import model.customerModel;
import model.divisionModel;
import DataAccessControl.customerAccessControl;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class for adding a customer on the add customer form
 */

public class addCustomerController implements Initializable {
    public Button actionCancelAddCustomer;
    public ComboBox<divisionModel> selectStateComboBox;
    public ComboBox<countryModel> selectCountryComboBox;
    public Button addCustomerButton;
    public TextField inputCustomerName;
    public TextField inputCustomerAddress;
    public TextField inputPostalCode;
    public TextField inputCustomerPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        JDBC.openConnection();
        try {
            ObservableList<countryModel> allCountriesList = countryAccessControl.getCountries();



            selectCountryComboBox.setItems(allCountriesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    /**
     * cancels adding and returns to customer screen
     */
    public void actionCancelAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * when country is selected event is triggered setting the combo boxes for various divisions based on the country
     */
    public void actionCountrySelected(ActionEvent actionEvent) throws SQLException {
        countryModel selectedCountry=selectCountryComboBox.getValue();
        System.out.println(" selectedCountry is " + selectedCountry.getCountryID() + " " + selectedCountry.getCountryName());



        ObservableList<divisionModel> USAList = divisionAccessControl.getCountryDivisions(selectedCountry.getCountryID());
        selectStateComboBox.setItems(USAList);

    }
    /**
     * customer is added after various input validations
     */

    public void actionAddCustomer(ActionEvent actionEvent) throws SQLException {
        JDBC.openConnection();

        try {

            if (!inputCustomerName.getText().isEmpty()  || !inputCustomerAddress.getText().isEmpty() || !inputPostalCode.getText().isEmpty() || !inputCustomerPhone.getText().isEmpty() ) {

                divisionModel selectedDivision = selectStateComboBox.getValue();
                int selectedDivisionID = selectedDivision.getDivisionID();

                String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (NULL,?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, inputCustomerName.getText());
                ps.setString(2, inputCustomerAddress.getText());
                ps.setString(3, inputPostalCode.getText());
                ps.setString(4, inputCustomerPhone.getText());

                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "shwetha");
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, "shwetha");

                ps.setInt(9, selectedDivisionID);

                ps.execute();


                inputCustomerName.clear();
                inputCustomerAddress.clear();
                inputPostalCode.clear();
                inputCustomerPhone.clear();

                Parent root = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else if (selectStateComboBox.getValue() == null  || selectCountryComboBox.getValue()==null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select Country and State. Enter correct information in all fields");
                alert.showAndWait();

            }

            else {

                Alert alert = new Alert(Alert.AlertType.ERROR, "One or more fields are empty. Enter the right information");
                alert.showAndWait();
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
}
