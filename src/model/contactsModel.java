package model;
/**
 * model class for contacts
 *
 */

public class contactsModel {
    public int contactID;
    public String contactName;
    public String contactEmail;

    public contactsModel(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     *
     * getter
     */
    public int getContactID() {

        return contactID;
    }

    /**
     *
     * getter
     */
    public String getContactName() {

        return contactName;
    }
    @Override
    public String toString(){
        return (contactName);
    }

    /**
     *
     * getter
     */
    public String getContactEmail() {

        return contactEmail;
    }
}
