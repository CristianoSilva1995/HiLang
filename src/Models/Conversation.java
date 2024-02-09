package Models;

/**
 * Model for Conversation db table
 * @author Arkadiusz Nowak
 */

public class Conversation {
    private int idConversation;
    private int idSubContext;
    private int idLanguage;
    private String languageLevel;
    private String grammarStructure;
    private String vocabulary;
    private String dateTimeCreated;

    public Conversation(int idConversation, int idSubContext, int idLanguage, String languageLevel,
                        String grammarStructure, String vocabulary, String dateTimeCreated) {
        this.idConversation = idConversation;
        this.idSubContext = idSubContext;
        this.idLanguage = idLanguage;
        this.languageLevel = languageLevel;
        this.grammarStructure = grammarStructure;
        this.vocabulary = vocabulary;
        this.dateTimeCreated = dateTimeCreated;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdSubContext() {
        return idSubContext;
    }

    public void setIdSubContext(int idSubContext) {
        this.idSubContext = idSubContext;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public String getGrammarStructure() {
        return grammarStructure;
    }

    public void setGrammarStructure(String grammarStructure) {
        this.grammarStructure = grammarStructure;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
