package DataAccessControl;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.divisionModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Gets sql data from divisions table and puts it in an observable list
 */

public class divisionAccessControl {
    public static ObservableList<divisionModel> getDivisions() throws SQLException {
        ObservableList<divisionModel> divisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                divisionModel division = new divisionModel(divisionID, divisionName, countryID);
                divisionsObservableList.add(division);

        }

        return divisionsObservableList;

    }
    public static ObservableList<divisionModel> getCountryDivisions(int selectedCountryID) throws SQLException {
        ObservableList<divisionModel> USDivisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions WHERE Country_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,selectedCountryID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");
            divisionModel division = new divisionModel(divisionID, divisionName, countryID);
            USDivisionsObservableList.add(division);

        }

        return USDivisionsObservableList;

    }






}
