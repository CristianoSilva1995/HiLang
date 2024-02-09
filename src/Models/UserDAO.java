package Models;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.PasswordUtil;

/**
 * UserDAO
 * Methods to fetch user data from Database
 * @author Cristiano Silva, Arkadiusz Nowak, Johnathan
 */

public class UserDAO {
    private Connection db;
    private PreparedStatement preparedStatement;
    private ResultSet res;
    private List<User> user = new ArrayList<User>();

    //Sets the Connection db
    public UserDAO(Connection db){
        // Make sure a valid db reference is passed
        if (db == null) {
            throw new NullPointerException();
        }
        this.db = db;
    }

    //method to create the new user passed through the controller
    //returns true if successfully
    public Boolean createNewUser(String userName, String firstName, String lastName, String password, String passwordSecret, String email, int userType){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter dateFormatWithTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            String query = "INSERT INTO user(userName, firstName, lastName, password, passwordSecret, email, registeredDate, lastAccess, userType) \n" +
                    "VALUES (?,?,?,?,?,LOWER(?),?,?,?)";
            PreparedStatement createNewUserStatement = db.prepareStatement(query);
            createNewUserStatement.setString(1, userName);
            createNewUserStatement.setString(2, firstName);
            createNewUserStatement.setString(3, lastName);
            createNewUserStatement.setString(4, password);
            createNewUserStatement.setString(5, passwordSecret);
            createNewUserStatement.setString(6, email);
            createNewUserStatement.setString(7, dateFormat.format(now));
            createNewUserStatement.setString(8, dateFormatWithTime.format(now));
            createNewUserStatement.setInt(9, userType);
            createNewUserStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //verify if the userName is on the DB
    //if it is, retrieves the salt
    public String verifyUserLogin(String userName) throws Exception{
        if(userName.isBlank() || userName == null){
            throw new Exception();
        }
        String secretKey;
        try {
            String query = "SELECT passwordSecret FROM user WHERE userName = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                secretKey = res.getString("passwordSecret");
                return secretKey;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Nothing found!";
    }

    //Boolean - Auth to validate the username and the password
    public Boolean auth(String userName, String password) throws Exception{
        if(userName.isBlank() || password.isBlank() || userName == null || password == null){
            throw new Exception();
        }

        try {
            String query = "SELECT idUser FROM user WHERE userName =  ? AND password = ?";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //Verifies if the username exists on the db
    //username is a unique field
    public Boolean verifyIfUsernameExists(String userName) throws Exception{
        if(userName.isBlank() || userName == null){
            throw new Exception();
        }

        String query = "SELECT * FROM user WHERE LOWER(userName) = LOWER(?)";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // Retrieves idUser for a given username (exact match)
    public Integer getUserIdByName(String username) throws Exception {
        if (username == null || username.isBlank()) {
            throw new Exception();
        }
        try {
            String query = "SELECT idUser FROM user WHERE LOWER(userName) =  LOWER(?)";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                return Integer.parseInt(res.getString("idUser"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Retrieves a list of users whose names match given string
    public HashMap<Integer, String> findUsersByName(String username) throws Exception {
        if (username == null || username.isBlank()) {
            throw new Exception();
        }
        // HashMap holding idUser, username pairs
        HashMap<Integer, String> users = new HashMap<>();
        try {
            String query = "SELECT idUser, username FROM user WHERE LOWER(userName) LIKE LOWER(?)";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, "%" +username + "%");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                // Add record to the list
                users.put(Integer.parseInt(res.getString("idUser")),res.getString("username"));
            }
            // Return results
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieves id of User by the given name and email
    public Integer getUserIdByNameAndEmail(String username, String email) throws Exception {
        if (username == null || email == null || username.isBlank() || email.isBlank()) {
            throw new Exception();
        }
        try {
            String query = "SELECT idUser FROM user WHERE LOWER(userName) =  LOWER(?) AND email = LOWER(?)";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet res = preparedStatement.executeQuery();
            Integer idUser = null;
            while (res.next()){
                idUser = Integer.parseInt(res.getString("idUser"));
            }
            return idUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Changes user password by given user id and new password
    public Boolean changePassword(Integer userId, String password) {
        // Make sure the parameters passed are valid
        if (userId == null || userId < 1 || password == null || password.isBlank()) {
            return false;
        }
        // Try to execute the update query
        try {
            // Generate new salt and encrypt password
            PasswordUtil passwordUtil = new PasswordUtil();
            String salt = passwordUtil.getSalt(100);
            String passwordEncrypted = passwordUtil.generateSecurePassword(password, salt);

            // Update user record
            String query = "UPDATE user SET password = ?, passwordSecret = ? WHERE idUser = ?";
            PreparedStatement res = db.prepareStatement(query);
            res.setString(1, passwordEncrypted);
            res.setString(2, salt);
            res.setInt(3, userId);
            // Save result of the update
            Boolean success =  res.executeUpdate() > 0;
            res.close();
            return success;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Verifies if the email exists on the db
    //email is a unique field
    public Boolean verifyIfEmailExists(String email) throws Exception{

        if(email.isBlank() || email == null){
            throw new Exception();
        }
        String query = "SELECT idUser FROM user WHERE email = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, email);
            res = preparedStatement.executeQuery();

            while (res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //retrieves the first and last name with the ID passed from the model
    public String getUserNameById(Integer idUser){

        String query = "SELECT firstName, lastName FROM user WHERE idUser = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            res = preparedStatement.executeQuery();

            while (res.next()){
                return res.getString("firstName") + " " + res.getString("lastName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    //johny adding method: method to retrieve a specific user's user type from the DB given their userId
    public Integer getUserTypeById(Integer idUser) {

        String query = "SELECT userType FROM user WHERE idUser = ?";
        try {
            preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            res = preparedStatement.executeQuery();

            while (res.next()){
                return res.getInt("userType");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}