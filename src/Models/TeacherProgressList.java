package Models;

import utils.Validate;
import javax.swing.DefaultListModel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Model for Teacher Progress page
 * @author Arkadiusz Nowak
 */

public class TeacherProgressList {
    private Connection db;
    private String search;
    private DefaultListModel<String> listModel;
    private HashMap<Integer, String> searchResults = new HashMap<>();

    public void setDb(Connection db) {
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    // Verify if submitted form data is valid
    public String verifyUserInput() {
        // Validate username
        if (!Validate.isValidUsername(search)) {
            return "Username is not valid or empty!";
        }

        // All checks have passed
        return "";
    }

    // Find users by given name and save the results into the model,
    // else return error msg
    public String findUser() {
        UserDAO user = new UserDAO(db);
        try {
            searchResults = user.findUsersByName(search);

            if (searchResults.isEmpty()) {
                return"Username not found!";
            } else { // User found, proceed
                //System.out.println("Elements: " + searchResults.size());
                setListModel(new DefaultListModel());
                for(Map.Entry<Integer, String> item: searchResults.entrySet()) {
                    listModel.addElement(item.getValue());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public HashMap<Integer, String> getSearchResults() {
        return searchResults;
    }
}
