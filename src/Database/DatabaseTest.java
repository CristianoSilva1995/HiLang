package Database;

import Models.*;
import config.Config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DatabaseTest {
    public static ArrayList<String> contextArrayList = new ArrayList<>();
    private static Config config;
    private static Connection db;
    private static UserDAO userDAO;
    private static ConversationHistoryDAO conversationHistoryDAO;

    
    public static void main(String[] args) {
        Config config = new Config();
        db = Database.getConnection(config.getProperty("db.name"));

        testDatabase();
        addDataToTest(100);
        addLanguages();

    }
    //added users students
    //add stats
    public static void testDatabase() {
        // Database test
        Statement stmt = null;
        ResultSet res = null;
        Connection db;

        // Read app config
        Config config = new Config();

        try {
            // Create new db connection
            db = Database.getConnection(config.getProperty("db.name"));
            System.out.println("Connection to SQLite established. Retrieving list of contexts...");

            // Retrieve a list of contexts
            stmt = db.createStatement();
            res = stmt.executeQuery("SELECT idContext, contextName FROM context ORDER BY idContext");
            System.out.println("-".repeat(40));
            while (res.next()) {
                System.out.println("#" + res.getInt("idContext") + " " + res.getString("contextName"));
                contextArrayList.add(res.getString("contextName"));
            }
            System.out.println("-".repeat(40) + "\n");
            res.close();
            stmt.close();
            db.close(); // Close db connection
        }  catch (SQLException e) {
            System.out.println("SQL threw error");
            System.out.println(e);
        }
    }

    public static void addDataToTest(Integer numberOfUser){
        User user = new User();
        user.setDb(db);
        for(int i = 1; i <= numberOfUser; i++){
            user.setPasswordEncrypt("12345678");
            user.getPasswordSecret();
            System.out.println("------------------------------------------------------------------------------------------------------");

            user.setUserName("User" + i);
            user.setFirstName("firstName" + i);
            user.setLastName("lastName" + i);
            user.setEmail("user"+i+"west.uk");
            user.setUserType(0);
            System.out.println("  Username: " + user.getUserName()+"\n  Email: " + user.getEmail());
            System.out.println("    - Password: "+ user.getPassword()
                    + "\n    - Salt: " + user.getPasswordSecret());
            user.createAccount();
            try {
                addConversation(numberOfUser,user.getIdUser());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addConversation(Integer numberOfInteractions, Integer idUser){
        Random random = new Random();
        final Integer randomConversation = 60;
        final Integer randomNumberAttempts = 20;
        for(int i = 0; i < random.nextInt(numberOfInteractions) + 1; i++){
            ConversationHistory conversationHistory = new ConversationHistory(idUser, 2, random.nextInt(randomConversation) + 1," ", " ", random.nextInt(randomNumberAttempts) + 1, random.nextBoolean());
            conversationHistory.setDb(db);
            conversationHistory.createConversationHistory();

        }
    }

    public static void addLanguages(){
        Map<String, String> languages = new HashMap<>();
        Language language = new Language();
        language.setDb(db);
        languages.put("Portuguese", "germany64.png");
        languages.put("German", "germany64.png");
        languages.put("Mandarin", "spain.png");
        languages.put("Japanese", "germany64.png");
        languages.put("Italian", "germany64.png");
        languages.put("Swahili", "spain.png");
        languages.put("Polish", "germany64.png");
        for(Map.Entry<String, String> entry : languages.entrySet()) {
            language.setLanguageName(entry.getKey());
            language.setImgFile(entry.getValue());
            language.addLanguageToDB();
        }
    }

}
