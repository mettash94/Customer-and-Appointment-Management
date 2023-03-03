package model;

/**
 * model class for divisions
 */

public class divisionModel {
    private int divisionID;
    private String divisionName;
    public int countryID;

    /**
       * constructor
     */
    public divisionModel(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     *Getter
     * @return divisionID
     */
    public int getDivisionID() {

        return divisionID;
    }

    /**
     * Getter
     * @return divisionName
     */
    public String getDivisionName() {

        return divisionName;
    }
    @Override
    public String toString(){
        return (divisionName);
    }

    /**
     * Getter
     * @return countryID
     */
    public int getcountryID() {

        return countryID;
    }
}
