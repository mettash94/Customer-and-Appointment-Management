package DataAccessControl;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.countryModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Gets sql data on countries and puts it in an observable list
 */

public class countryAccessControl {


    public static ObservableList<countryModel> getCountries() throws SQLException {

        ObservableList<countryModel> countriesObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            countryModel country = new countryModel(countryID, countryName);

            countriesObservableList.add(country);
        }
        return countriesObservableList;
    }

}
