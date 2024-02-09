package Controllers;

import Database.Database;
import Models.User;
import View.LoginPageView;
import config.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
/**
 * Login Controller
 */
public class LoginController{
    private User model;
    private LoginPageView view;
    private Connection db;
    private Config config;
    private AppController appController;
    private String errorMessage;

    //Retrieves from the appController the view, model use and the AppController
    //Defines config
    //sets the Connection to the DB
    public LoginController(User model, LoginPageView view, AppController appController){
        this.view = view;
        this.model = model;
        this.config = new Config();
        this.db = Database.getConnection(config.getProperty("db.name"));
        this.appController = appController;
        model.setDb(db);
        initView();
        initController();
    }
    //Initialize the view
    public void initView(){
        view.setTitle(config.getProperty("pages.LoginPage.title"));
        setViewSize();
        view.setLocationRelativeTo(null);
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoLabel().setIcon(iconLogo);

        Icon iconUser = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/userIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getUserIcon().setIcon(iconUser);

        Icon iconPassword = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/passwordIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPasswordIcon().setIcon(iconPassword);

        Icon iconSignUp = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/signUpIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getSignUpIcon().setIcon(iconSignUp);

        view.setVisible(true);
    }

    private void setViewSize() {
        view.setSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    //Initialize controller
    //addedActionListener
    public void initController(){
        //LoginButton when clicked retrieves calls getViewInfo method
        view.getLoginButton().addActionListener(e -> getViewInfo());
        //signUpButton when clicked retrieves calls navigateToSignUpPage method
        view.getSignUpButton().addActionListener(e -> navigateToSignUpPage());
        //ForgotPassword adding a MouseListener to detect the MouseInteraction
        view.getForgotPassword().addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            //Navigate to ChangePasswordPage through the appController, which calls the ChangePasswordController
            //disposes the current view
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                appController.navigateToChangePassword();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }
    //Calls AppController to navigate to signUpPage
    //Disposes current view
    public void navigateToSignUpPage(){
        view.dispose();
        appController.navigateToSignPage();
    }

    //retrieves info from the view
    public void getViewInfo(){
        //Validates the data inserted by the user
        if(model.validateInputLogin(view.getUserText().getText(), view.getPasswordText().getPassword()).isBlank()){
            //sets on the model the username and the password
            model.setUserName(view.getUserText().getText());
            model.setPassword(view.getPasswordText().getPassword());
            //calls auth on the model to validate login
            //if successfully displays login message and navigates to the next View
            //disposes the current view
            if(model.auth()){
                updateLoginMessage();
                appController.setCurrentIdUser(model.getIdUser());
                model.getUserTypeDB(model.getUserName());
                appController.setCurrentAccountType(model.getUserType());
                appController.navigateSelectLanguage();
                view.dispose();
            }else {
                //if not, displays an error message
                errorMessage = "Login has failed";
                updateLoginError();
            }
        }else{
            errorMessage = model.validateInputLogin(view.getUserText().getText(), view.getPasswordText().getPassword());
            updateLoginError();
        }
    }
    //method used by the controller to display messages on the view
    public void updateLoginMessage(){
        view.printUsername(model.getUserName());
    }
    //method used by the controller to display messages on the view
    public void updateLoginError(){ view.printError(errorMessage); }

}
