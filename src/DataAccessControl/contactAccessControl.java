package DataAccessControl;


import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contactsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Gets sql data from contacts table and puts it in an observable list
 */

public class contactAccessControl {
    public static ObservableList<contactsModel> getContacts() throws SQLException {
        ObservableList<contactsModel> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            contactsModel contact = new contactsModel(contactID, contactName, contactEmail);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }



}
