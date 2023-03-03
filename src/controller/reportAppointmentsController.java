package controller;

import DataAccessControl.reportsAccessControl;
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
import model.reportMonthModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 *Controller for showing reports of specific type
 */

public class reportAppointmentsController {
    public ComboBox typeComboBox;
    public TableColumn MonthColumn;
    public TableColumn totalColumn;
    public TableView reportTypeMonthTotalTable;

    /**
     * intializes combo box where you can pick a tyoe
     */

    public void initialize() throws SQLException {

        ObservableList<String> typeList= FXCollections.observableArrayList();

        typeList.add(0,"De-Briefing");
        typeList.add(1,"Planning Session");

        typeComboBox.setItems(typeList);
    }


    /**
     * action back to main menu
     */
    public void actionBackToReportsMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reportsScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * when type is selected it shows months and no of appointments per month
     */

    public void actionTypeSelected(ActionEvent actionEvent) throws SQLException {

        String selectedType= (String) typeComboBox.getValue();

        System.out.println(selectedType);

        ObservableList<reportMonthModel> monthAndTotalList= reportsAccessControl.getReportTypeMonthTotal(selectedType);

        reportTypeMonthTotalTable.setItems(monthAndTotalList);

        MonthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentByMonth"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentByMonthTotal"));




    }
}
