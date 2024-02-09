package Controllers;

import Models.User;
import View.SignUpPageView;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
/**
* SignUp Controller
 */
public class SignUpController {
    private SignUpPageView view;
    private User model;
    private AppController appController;
    private Connection db;
    private Config config;

    private final Integer STUDENT_ACCOUNT = 0;
    private final Integer TEACHER_ACCOUNT = 1;

    //Retrieves from the appController the view, model use and the AppController
    //Defines config
    //sets the Connection to the DB
    public SignUpController(SignUpPageView view, User model, AppController appController){
        this.view = view;
        this.model = model;
        this.appController = appController;
        this.config = appController.getConfig();
        this.db = appController.getDb();
        this.model.setDb(db);
        initView();
        initController();
    }
    //Initializes the view
    public void initView(){
        view.setTitle(config.getProperty("pages.SignUp.title"));
        setViewSize();
        view.setLocationRelativeTo(null);
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoIcon().setIcon(iconLogo);

        Icon iconUser = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/userIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPersonIconUsername().setIcon(iconUser);

        Icon iconUserFirstName = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/userIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPersonIconFirstName().setIcon(iconUserFirstName);

        Icon iconUserLastName = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/userIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPersonIconLastName().setIcon(iconUserLastName);

        Icon iconEmail = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/emailIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getEmailIcon().setIcon(iconEmail);

        Icon iconPassword = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/passwordIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPasswordIcon().setIcon(iconPassword);

        Icon iconPasswordConfirm = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/passwordIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getPasswordIcon2().setIcon(iconPasswordConfirm);

        view.setTypeAccountGroup(view.getTeacherRadioButton());
        view.setTypeAccountGroup(view.getStudentRadioButton());

        view.setVisible(true);
    }
    //Method to set the view size
    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    //Initializes controller
    //Adds an actionListener to createAccountButton()
    //Calls getAccountInfo()
    public void initController(){
        view.getCreateAccountButton().addActionListener(e -> getAccountInfo());
        view.getLogoIcon().addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            //Navigate to loginPage through the appController, which calls the LoginPageController
            //disposes the current view
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                appController.navigateToLogin();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }

    //Retrieves the information inserted by the user on the view
    public void getAccountInfo(){
        //Validates the user input through the model
        if(model.validateInputSignUp(view.getUsernameText().getText(),view.getPassText().getPassword(), view.getConPassText().getPassword(), view.getEmailText().getText()).equals("")){
           model.setUserName(view.getUsernameText().getText());
           model.setFirstName(view.getFirstnameText().getText());
           model.setLastName(view.getSurnameText().getText());
           model.setEmail(view.getEmailText().getText());
           //retrieves from the view which radioButton is selected
           //sets the userTypeAccount
           if(view.getTeacherRadioButton().isSelected()){
               model.setUserType(TEACHER_ACCOUNT);
           }else if(view.getStudentRadioButton().isSelected()){
               model.setUserType(STUDENT_ACCOUNT);
           }else{
               //updates the view to alert the user to select a userAccount
               updateView("Select the account type!");
           }
           //validates if the userName chosen does not exist on the database
           if(!model.verifyIfUsernameExists()){
               //validates if the email chosen does not exist on the database
               if(!model.verifyIfEmailExists()) {
                   //creates an account, disposes the view and calls the appController to navigate to the nextView
                   // if error, displays an errorMessage on the view
                   if (model.createAccount()) {
                       updateView("Account created successfully!");
                       view.dispose();
                       appController.setCurrentIdUser(model.getIdUser());
                       appController.setCurrentAccountType(model.getUserType());
                       appController.navigateSelectLanguage();
                   }else {
                       updateView("An error has occurred while creating your account!");
                   }
               }else{
                   updateView("Email already in use!");
               }
           }else{
               updateView("Username already in use!");
           }
        }else{
            updateView(model.validateInputSignUp(view.getUsernameText().getText(),view.getPassText().getPassword(), view.getConPassText().getPassword(), view.getEmailText().getText()));
        }
    }
    //method to display messages on the view
    public void updateView(String message){ view.printMessage(message); }
}
