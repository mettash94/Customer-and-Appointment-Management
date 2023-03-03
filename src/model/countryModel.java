package model;

/**
 * model class for countries
 */



public class countryModel {
    private int countryID;
    private String countryName;

    /**
     * constructor
     */
    public countryModel(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;

}
    /**
     * Getter
     * @return countryID
     */
    public int getCountryID() {

        return countryID;
    }

    /**
     * Getter
     * @return countryName
     */
    public String getCountryName() {

        return countryName;
    }

    @Override
    public String toString(){
        return (countryName);
    }
}

