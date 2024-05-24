package raven.model;

/**
 *
 * @author Raven
 */
public class ModelUser {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ModelUser(String userName, boolean admin) {
        this.userName = userName;
        this.admin = admin;
    }

    public ModelUser() {
    }

    private String userName;
    private boolean admin;
}
