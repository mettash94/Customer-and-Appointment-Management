package model;

/**
 * model class for users
 */

public class userModel {
    public int userID;
    public String userName;
    public String userPassword;

    public userModel(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * getter
     */
    public int getUserID() {

        return userID;
    }

    /**
     * getter
     */
    public String getUserName() {

        return userName;
    }

    @Override
    public String toString(){
        return (userName);
    }

    /**
     * getter
     */
    public String getUserPassword() {

        return userPassword;
    }
}
