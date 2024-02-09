package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * LanguageDAO
 * Fetches languages details from DB
 * @author Cristiano Silva
 */
public class LanguageDAO {
    private Connection db;
    private ResultSet res;
    private PreparedStatement preparedStatement;
    private List<Language> languageList = new ArrayList<>();

    //Sets the db Connection
    public LanguageDAO(Connection db){ this.db = db; }

    //Query to retrieve all languages available on the database
    //Returns a list of Language
    public List<Language> getLanguages(){
        try {
            String query = "SELECT languageName, imgFile FROM language";
            preparedStatement = db.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                Language language = new Language(res.getString("languageName"),res.getString("imgFile"));
                languageList.add(language);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return languageList;
    }

    //Query to retrieve all difficulty levels available on the database
    //Returns a list of String
    public List<String> getDifficulty(){
        List<String> getDifficulty = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT languageLevel FROM conversation";
            PreparedStatement preparedStatement = db.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                getDifficulty.add(res.getString("languageLevel"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getDifficulty;
    }

    // Johny: added this method to retrieve the languageId (int) given a languageName
    public int getLanguageId(String languageName){
        int getLangId = 0;
        String query = "SELECT idLanguage FROM language WHERE languageName = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, languageName);
            res = preparedStatement.executeQuery();
            while (res.next()){
                getLangId = res.getInt("idLanguage");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getLangId;
    }

    public String getLanguageName(int idLanguage){
        String getLangName = "";
        String query = "SELECT languageName FROM language WHERE idLanguage = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setInt(1, idLanguage);
            res = preparedStatement.executeQuery();
            while (res.next()){
                getLangName = res.getString("languageName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getLangName;
    }
    //Inserting new language on the DB
    public Boolean addLanguageDB(String languageName, String imgFile){
        String query = "INSERT INTO language(languageName, imgFile) VALUES (?,?);";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, languageName);
            preparedStatement.setString(2, imgFile);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //verifies If Language Has Content
    public Boolean verifyIfLanguageHasContent(String languageName){
        String query = "SELECT * from language JOIN conversation c on language.idLanguage = c.idLanguage\n" +
                "         WHERE language.languageName = ?";
        try {
            PreparedStatement preparedStatement = db.prepareStatement(query);
            preparedStatement.setString(1, languageName);
            res = preparedStatement.executeQuery();
            while (res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
