package model;
/**
 * model class for customer total appointments
 */

public class customerTotalAppointmentsModel {
    public String customerName;
    public int appointmentTotal;

    /**
     *
     * constructor
     */
    public customerTotalAppointmentsModel(String customerName, int appointmentTotal) {
        this.customerName = customerName;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * getters
     */
    public String getCustomerName() {

        return customerName;
    }

    public int getAppointmentTotal() {

        return appointmentTotal;
    }
}
