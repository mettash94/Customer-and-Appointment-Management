package model;
/**
 * CustomerModel class is a model class for customer object used in DAO. It contains Customer attributes such as id, name,
 * address, postal code, number, division id and name
 * We will define getters and setters to access different attributes of the customer
 */

public class customerModel {


    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionID;
    private String divisionName;
    private int countryId;

    /**
     constructor for customerModel class
     */
    public customerModel(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int divisionID, String divisionName, int countryId) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryId=countryId;

    }

    /**
     * Getter
     * @return customerID
     */
    public Integer getCustomerID() {

        return customerID;
    }




    /**
     * setter
     */
    public void setCustomerID(Integer customerID) {

        this.customerID = customerID;
    }

    /**
     * getter
     * @return customerName
     */
    public String getCustomerName() {

        return customerName;
    }
    @Override
    public String toString(){
        return (customerName);
    }



    /**
     *
     * setter
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     * getter
     * @return customerAddress
     */
    public String getCustomerAddress() {

        return customerAddress;
    }

    /**
     *
     * setter
     */
    public void setCustomerAddress(String address) {

        this.customerAddress = address;
    }

    /**
     *getter
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {

        return customerPostalCode;
    }

    /**
     *
     * setter
     */
    public void setCustomerPostalCode(String postalCode) {

        this.customerPostalCode = postalCode;
    }

    /**
     * @return phone number
     * getter
     */
    public String getCustomerPhone() {

        return customerPhoneNumber;
    }

    /**
     *setter
     */
    public void setCustomerPhone(String phone) {

        this.customerPhoneNumber = phone;
    }

    /**
     *
     * @return divisionID
     */
    public Integer getCustomerDivisionID() {

        return divisionID;
    }
    /**
     *
     * setter
     */
    public void setCustomerDivisionID(Integer divisionID) {

        this.divisionID = divisionID;
    }

    /**
     *getter
     * @return divisionName
     */
    public String getDivisionName() {

        return divisionName;
    }
    /**
      * setter
     */
    public void setDivisionName(Integer divisionID) {

        this.divisionName = divisionName;
    }

    public int getCountryId(){
        return countryId;
    }



}
