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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.countryModel;
import model.customerModel;
import model.divisionModel;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * updates customer controller
 */

public class updateCustomerController implements Initializable {

    public TextField inputCustomerID;
    public TextField inputCustomerName;
    public TextField inputCustomerAddress;
    public TextField inputPostalCode;
    public TextField inputCustomerPhone;


    public ComboBox<countryModel> selectCountryComboBox;
    public ComboBox<divisionModel> selectStateComboBox;

    /**
     * initializes the update form with the customer selected info
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JDBC.openConnection();
        try {
            ObservableList<countryModel> allCountriesList = countryAccessControl.getCountries();

            selectCountryComboBox.setItems(allCountriesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    /**
     * when country is selected in the combo box it sets different divisions in the divison combo box
     */

    public void actionCountrySelected(ActionEvent actionEvent) throws SQLException {
        countryModel selectedCountry= selectCountryComboBox.getValue();


            ObservableList<divisionModel> USAList = divisionAccessControl.getCountryDivisions(selectedCountry.getCountryID());
            selectStateComboBox.setItems(USAList);

    }


    /**
     *customer data selected is initialized
     */


    public void getSelectedProduct(customerModel customer) throws SQLException {



        inputCustomerID.setText(String.valueOf(customer.getCustomerID()));
        inputCustomerName.setText(String.valueOf(customer.getCustomerName()));
        inputCustomerAddress.setText(String.valueOf(customer.getCustomerAddress()));
        inputPostalCode.setText(String.valueOf(customer.getCustomerPostalCode()));
        inputCustomerPhone.setText(String.valueOf(customer.getCustomerPhone()));




       int selectedCountryId=customer.getCountryId();

      for(countryModel C : selectCountryComboBox.getItems()){
          if (C.getCountryID()==selectedCountryId){
              selectCountryComboBox.setValue(C);

              break;
          }
      }

        ObservableList<divisionModel> USAList = divisionAccessControl.getCountryDivisions(selectedCountryId);
        selectStateComboBox.setItems(USAList);

        for(divisionModel C : selectStateComboBox.getItems()){
            if (C.getDivisionID()==customer.getCustomerDivisionID()){
                selectStateComboBox.setValue(C);

                break;
            }
        }




    }

    /**
     * cancel update
     * @param actionEvent
     * @throws IOException
     */
    public void actionCancelUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * update customer after data validaiton
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */

    public void actionUpdateCustomer(ActionEvent actionEvent) throws SQLException, IOException {

        JDBC.openConnection();

        try {

            if (!inputCustomerName.getText().isEmpty() || !inputCustomerAddress.getText().isEmpty() || !inputPostalCode.getText().isEmpty() || !inputCustomerPhone.getText().isEmpty()) {

                divisionModel selectedDivision = selectStateComboBox.getValue();
                int selectedDivisionID = selectedDivision.getDivisionID();

                String sql = "UPDATE customers SET  Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);


                ps.setString(1, inputCustomerName.getText());
                ps.setString(2, inputCustomerAddress.getText());
                ps.setString(3, inputPostalCode.getText());
                ps.setString(4, inputCustomerPhone.getText());

                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "shwetha");
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, "shwetha");

                ps.setInt(9, selectedDivisionID);
                ps.setString(10, inputCustomerID.getText());


                ps.execute();

                Parent root = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
        }
         catch (Exception e){
                e.printStackTrace();
            }



        }
    }

