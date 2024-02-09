package Models;
/* AUTHOR: Jonathan Carrillo-Sanchez
 *  ID: w1758229 */

import java.sql.Connection;

public class HomeModel {
    private Connection db;
    private LanguageDAO languageDAO;
    private UserDAO userDAO;
    // default constructor
    public HomeModel() {

    }
    // Sets the DB connection in order to use DAO classes for home page.
    public void setDb(Connection db){
        this.db = db;
    }

    // Using DAO method to retrieve language name given the languageId
    public String getLanguageNameDB(Integer languageId) {
        languageDAO = new LanguageDAO(db);
        return languageDAO.getLanguageName(languageId);
    }
    // Using DAO method to retrieve the user type given the userId
    public Integer getUserTypeDB(Integer userId) {
        userDAO = new UserDAO(db);
        return userDAO.getUserTypeById(userId);
    }
}
