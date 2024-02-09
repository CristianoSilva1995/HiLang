package Models;

import java.sql.Connection;
import java.util.List;
/**
 * Language Model
 * Stores language details and communicates with the DAO
 * @author Cristiano Silva
 */
public class Language {
    private String languageName;
    private String imgFile;

    private LanguageDAO languageDAO;

    private Connection db;

    public Language(){
        languageName = null;
        imgFile = null;
    }

    public Language(String languageName, String imgFile){
        this.languageName = languageName;
        this.imgFile = imgFile;
    }

    public void setDb(Connection db){
        this.db = db;
    }

    public String getLanguageName() { return languageName; }

    public String getImgFile() { return imgFile; }

    public void setLanguageName(String languageName) { this.languageName = languageName; }

    public void setImgFile(String imgFile) { this.imgFile = imgFile; }

    //Uses languageDAO to retrieve all languages available on the DB
    //Returns a list of Language
    public List<Language> getLanguages(){
        languageDAO = new LanguageDAO(db);
        return languageDAO.getLanguages();
    }

    //Uses LanguageDAO to retrieve all difficulty levels available
    //Returns a list of Strings
    public List<String> getDifficulty(){
        return languageDAO.getDifficulty();
    }

    // johny added this method so that it will work with cristianos 'SelectLanguageController'
    public int getLanguageIdByName(String languageName) {
        languageDAO = new LanguageDAO(db);
        return languageDAO.getLanguageId(languageName);
    }

    //calls method to insert language into DB through languageDAO
    public void addLanguageToDB(){
        languageDAO = new LanguageDAO(db);
        languageDAO.addLanguageDB(getLanguageName(), getImgFile());
    }
    //calls DAO to verify if there is any conversations attached to the language
    public Boolean verifyIfLanguageExists(){
        languageDAO = new LanguageDAO(db);
        return languageDAO.verifyIfLanguageHasContent(getLanguageName());
    }

}
