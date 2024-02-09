package Controllers;
import Database.Database;
import Models.*;
import View.*;
import config.Config;
import java.sql.Connection;

/**
 * Application's main controller:
 * - reads config file
 * - establishes connection to the database
 * - allows navigation between pages through their controllers by passing them their models and views
 * - stores some of logged in User's information used throughout the application
 */

public class AppController {
    private Connection db;
    private Config config;
    private Integer currentIdUser;
    private String currentDifficulty;
    private Integer currentAccountType;
    private final int TEACHER_LEVEL = 1;

    private Integer currentIdLanguage;

    public AppController() {
        // Read the configuration file
        readConfig();
        // Connect to Database
        connectDb();
        // On app run, route to login page
        navigateToLogin();
    }

    // Read app config
    private void readConfig() {
        // Making sure we only load the data once
        if (config == null) {
            this.config = new Config();
        }
    }

    public Config getConfig() {
        return this.config;
    }

    private void connectDb() {
        // Establish new connection to the database
        this.db = Database.getConnection(this.config.getProperty("db.name"));
    }

    public Connection getDb() {
        return this.db;
    }

    public void navigateToLogin() {
        LoginPageView view = new LoginPageView();
        User model = new User();
        new LoginController(model, view, this);
    }

    //Calls the SelectLanguage controller to navigate to Select Language
    public void navigateSelectLanguage() {
        SelectLanguageView view = new SelectLanguageView();
        Language model = new Language();
        new SelectLanguageController(view, model, this);
    }

    //Calls the SignUpController to navigate to SignUP Page
    public void navigateToSignPage() {
        SignUpPageView view = new SignUpPageView();
        User model = new User();
        new SignUpController(view, model, this);
    }

    // Navigates User to Change Password page
    public void navigateToChangePassword() {
        ChangePassword model = new ChangePassword();
        ChangePasswordView view = new ChangePasswordView();
        new ChangePasswordController(this, model, view);
    }

    // Navigates User to their Chat History
    // or, if Teacher, to previously selected User's Chat History
    public void navigateToChatHistory(Integer idUser) {
        ChatHistory model = new ChatHistory(idUser);
        ChatHistoryView view = new ChatHistoryView();
        new ChatHistoryController(this, model, view);
    }



    // Navigates Teacher to User search page
    public void navigateToTeacherProgressList() {
        TeacherProgressList model = new TeacherProgressList();
        TeacherProgressListView view = new TeacherProgressListView();
        new TeacherProgressListController(this, model, view);
    }
    public void navigateToContextAndSubcontext() {
        ContextAndSubcontext view = new ContextAndSubcontext();
        SubContext model = new SubContext();
        new ContextSubContextController(model,view, this);
    }

    public void navigateToTeacherProgress(Integer idUser) {
        try {
            TeacherProgress model = new TeacherProgress();
            TeacherProgressView view = new TeacherProgressView(idUser);
            new TeacherProgressController(view,model,this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateToTeacherViewChats(Integer idConversation) {
        try {
            TeacherViewChats view = new TeacherViewChats(this, idConversation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    Navigates to Home Page
//    Sends as parameters the userID, languageID and Difficulty chosen.
    public void navigateToHomePage(){
        HomeModel model = new HomeModel();
        Home view = new Home(getCurrentIdUser(),getCurrentIdLanguage(),getCurrentDifficulty());
        new HomeController(this,model,view);
    }

    //Navigates to Chat Page
    //Sends as parameters the subcontext id from appropriate models from previous class
    public void navigateToChatPage(Integer subContextId){
        ChatPageModel model = new ChatPageModel();
        ChatPage view = new ChatPage(getCurrentIdUser(),subContextId,getCurrentIdLanguage(),getCurrentDifficulty());
        new ChatPageController(this,model,view);
    }

    // Navigates to hard-coded suggestions from the home page
    public void navigateToChatPageFromJLabel(Integer subContextId){
        ChatPageModel model = new ChatPageModel();
        ChatPage view = new ChatPage(getCurrentIdUser(),subContextId,getCurrentIdLanguage(),getCurrentDifficulty());
        new ChatPageController(this,model,view);
    }

    public void navigateToStudentProgress() {

        try {
            StudentProgress model = new StudentProgress();
            StudentProgressView view = new StudentProgressView(getCurrentIdUser());
            StudentProgressController controller = new StudentProgressController(view,model, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getCurrentAccountType() {
        return currentAccountType;
    }
    public Integer getCurrentIdUser() {
        return currentIdUser;
    }

    public void setCurrentIdUser(Integer currentIdUser) {
        this.currentIdUser = currentIdUser;
    }

    public String getCurrentDifficulty() {
        return currentDifficulty;
    }

    public Integer getCurrentIdLanguage() {
        return currentIdLanguage;
    }

    public void setCurrentDifficulty(String currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }
    public void setCurrentAccountType(Integer currentAccountType) {
        this.currentAccountType = currentAccountType;
    }
    public void setCurrentIdLanguage(Integer currentIdLanguage) {
        this.currentIdLanguage = currentIdLanguage;
    }

    public int getTEACHER_LEVEL() {
        return TEACHER_LEVEL;
    }

    // Returns true if currently logged-in user is a teacher,
    // otherwise false
    public Boolean isUserTeacher() {
        if (getCurrentAccountType().equals(getTEACHER_LEVEL())) {
            return true;
        } else {
            return false;
        }
    }
}