package DataAccessControl;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.userModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets sql data from users table and puts it in an observable list
 */

public class userAccessControl {

    public static ObservableList<userModel> getUsers() throws SQLException {
        ObservableList<userModel> userObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            userModel user = new userModel(userID, userName, userPassword);
            userObservableList.add(user);
        }
        return userObservableList;
    }

    /**
     * method checks username and password and returns a boolean
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */

    public static boolean userValidation(String username, String password) throws SQLException {

        //String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";
        String sqlQuery2 = "SELECT * FROM users WHERE " + "User_Name = ? AND Password = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery2);

        ps.setString(1,username);
        ps.setString(2, password);

        ResultSet result = ps.executeQuery();


        if (!result.next()) {
            ps.close();
            return false;

        }
        return true;
    }

    /**
     * method returns user id if user is present otherwise 0
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static int getUserID(String username, String password) throws SQLException {

            String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";

            PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("User_Name").equals(username)) {
                if (rs.getString("Password").equals(password)) {
                    return rs.getInt("User_ID");

                }
            }


        return 0;

    }
    }


