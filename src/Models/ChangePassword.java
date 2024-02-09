package Models;

import utils.Validate;
import java.sql.Connection;

/**
 * Model for Change Password page
 */

public class ChangePassword {
    private String username;
    private String email;
    private String password;
    private Connection db;

    public void setDb(Connection db) {
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Change user password once all validations are passed (user found)
    public String changeUserPassword() {
        // Check if an account with given username and email exist
        UserDAO user = new UserDAO(db);
        try {
            // Find user by given name and email and store their id, or set to null if not found
            Integer userId = user.getUserIdByNameAndEmail(username, email);
            if (userId == null) { // User not found
                return "Username or email address not found!";
            } else { // User found, change password
                Boolean isChanged = user.changePassword(userId, password);
                if (isChanged) {
                    return "Password for user " + username + " " +
                            "("+email+") changed successfully!";
                    // @TODO go back to home page
                } else { // Something went wrong
                    return "Failed to change password! Please try again.";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Database error! Exception caught.";
        }
    }

    // Verify whether the input data submitted by the user is valid for it's given types
    public String verifyUserInput() {
        // Validate username
        if (!Validate.isValidUsername(username)) {
            return "Username is not valid or empty!";
        }

        // Validate email
        if (!Validate.isValidEmail(email)) {
            return "Email address is not valid or empty!";
        }

        // Validate password
        if (!Validate.isValidPassword(password)) {
            return "Password is not valid or empty!";
        }

        // All checks have passed
        return "";
    }
}