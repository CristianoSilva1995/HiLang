import Controllers.*;
import Models.*;
import View.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Database.*;

import javax.swing.*;

public class Main {
    // https://stackoverflow.com/questions/22313120/swingutilities-invokelater-takes-a-runnable-and-runs-it-on-the-edt#22313310
    // main is not executed in the EDT
    // @TODO Remember to remove console menu once other views have been sorted and tested!
    public static void main(String[] args) {
        // Leaving for later, once other mess gets sorted out
        // create and initialize GUI in the EDT to avoid potential thread-safety issues.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }

            private void createAndShowGUI() {
                new AppController();
            }
        });

/*        // @TODO REMOVE LATER
        AppController app = new AppController();

        // Menu options
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("1 - Select Language");
        menuOptions.add("2 - Teacher Progress");
        menuOptions.add("3 - Login");
        menuOptions.add("4 - SignUp");
        menuOptions.add("5 - Context");
        menuOptions.add("6 - Chat History (Student / Teacher)");
        menuOptions.add("7 - Change Password");
        menuOptions.add("8 - HomePage");
        menuOptions.add("9 - Test database");
        menuOptions.add("10 - ChatPage");
        menuOptions.add("11 - Teacher Progress List");
        menuOptions.add("\nOption: ");

        // Run menu
        Scanner scan = new Scanner(System.in);
        int opt = -1;
        while(opt != 0){
            System.out.print(String.join("\n", menuOptions));
            opt = scan.nextInt();
            if (opt == 1) {
                SelectLanguageView view = new SelectLanguageView();
                Language model = new Language();
                //SelectLanguageController controller = new SelectLanguageController(view, model, app);
            } else if (opt == 2) {
                try {
                    TeacherProgressView view = new TeacherProgressView(10);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (opt == 3) {
                LoginPageView view = new LoginPageView();
                User model = new User();
                LoginController loginController = new LoginController(model,view, app);
            } else if (opt == 4) {
                SignUpPageView view = new SignUpPageView();
                User model = new User();
                SignUpController signUpController = new SignUpController(view, model, app);
            } else if (opt == 5) {
                ContextAndSubcontext view = new ContextAndSubcontext();
                SubContext model = new SubContext();
                ContextSubContextController controller = new ContextSubContextController(model, view, app);
            } else if (opt == 6) {
                app.setCurrentIdUser(1);
                app.setCurrentAccountType(1);
                ChatHistory model = new ChatHistory(10);
                ChatHistoryView view = new ChatHistoryView(); // default user
                ChatHistoryController controller = new ChatHistoryController(app, model, view);
            } else if (opt == 7) {
                ChangePassword model = new ChangePassword();
                ChangePasswordView view = new ChangePasswordView();
                ChangePasswordController controller = new ChangePasswordController(app, model, view);
            } else if (opt == 8) {
                Home view = new Home(1, 1, "A1");
            }  else if (opt == 9) {
                DatabaseTest testDb = new DatabaseTest();
                testDb.testDatabase();
            } else if (opt == 10) {
                ChatPage chatPage = new ChatPage(1,1,1,"A1");
            } else if (opt == 11) {
                TeacherProgressList model = new TeacherProgressList();
                TeacherProgressListView view = new TeacherProgressListView();
                TeacherProgressListController controller = new TeacherProgressListController(app, model, view);
            }
        }*/
    }
}