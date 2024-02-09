package Controllers;

import Models.ChangePassword;
import View.ChangePasswordView;
import config.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.Objects;

/**
 * Controller for Change Password page
 */

public class ChangePasswordController {
    private AppController app;
    private ChangePassword model;
    private ChangePasswordView view;
    private Connection db;
    private Config config;

    /**
     *
     *  Constructor receives and stores references to AppController, ChangePassword model and
     *  ChangePasswordView view passed from AppController, reference to database connection, config, passes
     *  database connection reference to the model. As well as initializes the view with listeners.
     *
     */
    public ChangePasswordController(AppController app, ChangePassword model, ChangePasswordView view) {
        // Store parameters if not null, otherwise throw null pointer exception
        this.app = Objects.requireNonNull(app);
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);

        // Get app config
        this.config = app.getConfig();

        // Get Db link
        this.db = app.getDb();

        // Pass db connection to the model
        this.model = model;
        this.model.setDb(db);

        // Initialize and show view
        initView();
        initController();
    }

    // Set attributes of the view based on data passed from config and display it
    public void initView() {
        // Set title
        view.setTitle(config.getProperty("pages.ChangePassword.title"));

        // Set window size
        setViewSize();
        // Center window
        view.setLocationRelativeTo(null);

        // Setup icons
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").
                getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLblLogo().setIcon(iconLogo);

        Icon iconUser = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/userIcon.png").
                getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getLblUserIcon().setIcon(iconUser);

        Icon iconEmail = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/emailIcon.png").
                getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getLblEmailIcon().setIcon(iconEmail);

        Icon iconPassword = new ImageIcon(new ImageIcon(config.getProperty("images.icons")+"/passwordIcon.png").
                getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        view.getLblPasswordIcon().setIcon(iconPassword);

        // Show frame
        view.setVisible(true);
    }

    // Set window view size
    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    // Set view's listeners
    public void initController() {
        // Form submit button
        view.getBtnSubmit().addActionListener(e -> processUserInput());
        // Log in - adding a MouseListener to detect the MouseInteraction
        view.getLblLogIn().addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            //Navigate to Log in page through the appController
            //disposes the current view
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                app.navigateToLogin();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        });
        view.getLblSignup().addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            // Navigate to Sign Up page through the appController
            // dispose current view
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                app.navigateToSignPage();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });
    }

    // Pass the view data to the model in order to process it
    private void processUserInput() {
        // Get user input from the view and save it in the model
        saveFormDataToModel();
        // Verify user input
        String username = model.getUsername();
        String email = model.getEmail();
        String password = model.getPassword();
        String error; // Stores error msg received from model
        error = model.verifyUserInput();
        if (error.isEmpty()) {
            // Try to change user's password
            error = model.changeUserPassword();
            // If there's no error, proceed
            if (error.isEmpty()) {
                view.getDialog().showMessageDialog(view, "Password for user " + username + " " +
                                "("+email+") changed successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                // Return to login page
                view.dispose(); // Dispose the current view
                app.navigateToLogin(); // Have app controller navigate to login page
            } else { // If model returned error, display it
                view.getDialog().showMessageDialog(view, error,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // Display error returned by the model
            view.getDialog().showMessageDialog(view, error,
                    "Error", JOptionPane.ERROR_MESSAGE);
            error = "";
        }
    }

    // Save form view data
    public void saveFormDataToModel() {
        model.setUsername(view.getTxtUser().getText());
        model.setEmail(view.getTxtEmail().getText());
        model.setPassword(String.valueOf(view.getTxtPassword().getPassword()));
    }
}