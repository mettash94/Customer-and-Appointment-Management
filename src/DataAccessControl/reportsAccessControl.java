package DataAccessControl;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customerTotalAppointmentsModel;
import model.reportMonthModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets sql data for reports  and puts it in an observable list
 */

public class reportsAccessControl {

    public static ObservableList<reportMonthModel> getReportTypeMonthTotal(String type) throws SQLException {

        ObservableList<reportMonthModel> reportTypeMonthObservableList = FXCollections.observableArrayList();

        String sql = "SELECT MONTHNAME(Start) as \"Month\", COUNT(MONTH(Start)) as \"Total\" from appointments GROUP BY Month";
        String sql2="SELECT MONTHNAME(Start) as \"Month\", COUNT(MONTH(Start)) as \"Total\" from appointments WHERE Type=? GROUP BY Month";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql2);
        ps.setString(1,type);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String appointmentByMonth=rs.getString("Month");

            int appointmentByMonthTotal=rs.getInt("Total");

            reportMonthModel report= new reportMonthModel(appointmentByMonth,appointmentByMonthTotal);
            reportTypeMonthObservableList.add(report);

        }

        return reportTypeMonthObservableList;
    }





    public static ObservableList<customerTotalAppointmentsModel> getCustomerAndAppointmentsTotal() throws SQLException {

        ObservableList<customerTotalAppointmentsModel> customerTotalAppointmentsModelObservableList = FXCollections.observableArrayList();

        String sql = "SELECT Type, COUNT(Type) as  \"Total\" FROM appointments GROUP BY Type";

        String sql2= "SELECT  Customer_Name, COUNT(Customer_Name) as  \"Total\"  FROM client_schedule.appointments INNER JOIN client_schedule.customers ON appointments.Customer_ID = customers.Customer_ID GROUP BY Customer_Name";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql2);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String customerName=rs.getString("Customer_Name");

            int appointmentTotal=rs.getInt("Total");

            customerTotalAppointmentsModel newCustomer= new customerTotalAppointmentsModel(customerName,appointmentTotal);

            customerTotalAppointmentsModelObservableList.add(newCustomer);




        }


        return customerTotalAppointmentsModelObservableList;

    }
}
