package model;

/**
 * model class for reports for month and month totals
 */

public class reportMonthModel {
    public String appointmentByMonth;
    public int appointmentByMonthTotal;

    /**
     *
     * constructor
     */
    public reportMonthModel(String appointmentByMonth, int appointmentByMonthTotal) {
        this.appointmentByMonth = appointmentByMonth;
        this.appointmentByMonthTotal = appointmentByMonthTotal;
    }

    /**
     * getters
     */
    public String getAppointmentByMonth() {

        return appointmentByMonth;
    }

    public int getAppointmentByMonthTotal() {

        return appointmentByMonthTotal;
    }

}
