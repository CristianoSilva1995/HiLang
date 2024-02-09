package Models;
import utils.PasswordUtil;
import utils.Validate;
import java.sql.Connection;
/**
 * User Model
 * Getters and setters for User
 * Methods to communicate with the UserDAO
 * @author Cristiano Silva
 */
public class User {
    private Integer idUser;
    private String userName;
    private String firstName;

    private String lastName;
    private String password;
    private String passwordSecret;
    private String email;
    private String registeredDate;
    private String lastAccessed;
    private Integer userType;
    private Connection db;
    private UserDAO modelDAO;
    //TODO add comments
    private PasswordUtil passwordUtil = new PasswordUtil();

    public User() {

    }
    public User(String userName, String firstName, String lastName, String password, String email, String passwordSecret, Integer userType){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.passwordSecret = passwordSecret;
        this.userType = userType;
    }
    public User(String userName, String lastName){
        this.userName = userName;
        this.lastName = lastName;
    }

    public Integer getIdUser() { return idUser; }

    public String getUserName() { return userName; }

    public String getLastName() { return lastName; }

    public String getFirstName() { return firstName; }

    public String getPassword() { return password; }

    public String getPasswordSecret() {
        return passwordSecret;
    }

    public String getEmail() { return email;}

    public String getRegisteredDate() { return registeredDate; }

    public String getLastAccessed() { return lastAccessed; }

    public Integer getUserType() { return userType; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(char[] password) {
        this.password = String.valueOf(password);
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordSecret(String passwordSecret) {
        this.passwordSecret = passwordSecret;
    }

    public void setLastAccessed(String lastAccessed){
        this.lastAccessed = lastAccessed;
    }

    //Validate if user input is valid both for userName and password
    //Used on login
    public String validateInputLogin(String username, char[] password){
        String passTemp = String.valueOf(password);
        if(!Validate.isValidUsername(username)){
            return "Username is not valid or empty!";
        }
        if(!Validate.isValidPassword(passTemp)){
            return "Password is not valid or empty!";
        }
        return "";
    }
    //Validate if user input is valid both for userName and password
    //Validate if both passwords match
    //Validate if email is valid
    //Used on signUp
    public String validateInputSignUp(String username, char[] password, char[] passwordConfirmation, String email){
        String passTemp = String.valueOf(password);
        String passConfTemp = String.valueOf(passwordConfirmation);

        if(!Validate.isValidUsername(username)){
            return "Username is not valid or empty!";
        }
        if(!Validate.isValidPassword(passTemp) || !Validate.isValidPassword(passTemp)){
            return "Password is not valid or empty! (minimum  8 characters)";
        }
        if(!(passTemp.equals(passConfTemp))){
            return "Password does not match!";
        }
        if(!(Validate.isValidEmail(email))){
            return "Email address is not valid or empty!";
        }
        //if all the validation goes through encrypts the password
        setPasswordEncrypt(passTemp);
        return "";
    }

    //method to retrieve the salt from the DB and stores on userModel
    public void setPasswordSecretDB(){
        try {
            String passwordSecret = modelDAO.verifyUserLogin(getUserName());
            setPasswordSecret(passwordSecret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //sets the userId from the db
    public void setIdUser() {
        try {
            this.idUser = modelDAO.getUserIdByName(getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    //set db connection
    public void setDb(Connection db) {
        this.db = db;
        modelDAO = new UserDAO(db);
    }
    //retrieves the encrypted password from the db
    public String getPasswordEncrypt() {
        PasswordUtil passwordUtil = new PasswordUtil();
        setPasswordSecretDB();
        password = passwordUtil.generateSecurePassword(password, getPasswordSecret());
        setPassword(password);
        return password;
    }

    //method to validate username and password encrypted
    //communicates with the modelDAO
    public Boolean auth(){
        try {
            setIdUser();
            return modelDAO.auth(getUserName(),getPasswordEncrypt());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Encrypts the password and set the password which was encrypted and sets the salt
    public void setPasswordEncrypt(String password){
        String salt = passwordUtil.getSalt(100);
        String passwordEncrypted = passwordUtil.generateSecurePassword(password, salt);
        setPassword(passwordEncrypted);
        setPasswordSecret(salt);
    }

    //method to verify if userNameExists, communicates with the modelDAO
    public Boolean verifyIfUsernameExists() {
        try {
            return modelDAO.verifyIfUsernameExists(getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //method to verify if emailExists, communicates with the modelDAO
    public Boolean verifyIfEmailExists() {
        try {
            return modelDAO.verifyIfEmailExists(getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //method which communicate with the modelDAO to create an account.
    //call setIdUser to store the id
    public Boolean createAccount(){
        try {
            if(modelDAO.createNewUser(getUserName(),getFirstName(), getLastName(), getPassword(), getPasswordSecret(), getEmail(), getUserType())){
                setIdUser();
                return true;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    return false;
    }

    //calls the UserDAO to retrieve the userType
    public void getUserTypeDB(String userName){
        try {
            setUserType(modelDAO.getUserTypeById(modelDAO.getUserIdByName(userName)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
