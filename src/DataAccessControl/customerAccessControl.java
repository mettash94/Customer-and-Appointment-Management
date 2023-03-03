package DataAccessControl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.customerModel;

/**
 * Gets sql data from customers table and puts it in an observable list
 */

public class customerAccessControl {

    public static ObservableList<customerModel> getCustomers() throws SQLException {

        ObservableList<customerModel> customersObservableList = FXCollections.observableArrayList();

        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int ID = rs.getInt("Customer_ID");
            String Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String PostalCode = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int divID = rs.getInt("Division_ID");
            String divName = rs.getString("Division");
            int countryId=rs.getInt("Country_ID");

            customerModel newCustomer = new customerModel(ID, Name, Address, PostalCode, Phone, divID, divName, countryId);


            customersObservableList.add(newCustomer);
        }
        return customersObservableList;

    }

}