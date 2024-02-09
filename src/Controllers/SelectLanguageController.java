package Controllers;

import Models.Language;
import View.SelectLanguageView;
import config.Config;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.List;

/**
 * SelectLanguageController
 * @author Cristiano Silva
 */
public class SelectLanguageController implements ActionListener {
    private SelectLanguageView view;
    private Connection db;
    private Language model;

    private Config config;

    private AppController appController;

    private Hashtable<Integer, JLabel> sliderSubtitle = new Hashtable<>();

    private List<Language> languageList;

    private Integer idUser;

    //Retrieves from the appController the view, model use and the AppController
    //Defines config
    //sets the Connection to the DB
    public SelectLanguageController(SelectLanguageView view, Language model, AppController appController){
        this.appController = appController;
        this.view = view;
        this.model = model;
        this.config = appController.getConfig();
        this.db = appController.getDb();
        this.model.setDb(db);
        initView();
        initController();
    }

    //Initializes the SelectLanguageView
    private void initView(){
        view.setTitle(config.getProperty("pages.SelectLanguage.title"));
        setViewSize();
        view.setLocationRelativeTo(null);
        Icon iconLogo = new ImageIcon(new ImageIcon(config.getProperty("images")+"/logo.jpeg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        view.getLogoLabel().setIcon(iconLogo);

        //Retrieves a list of all Languages from the model (Language)
        languageList = model.getLanguages();

        if(languageList.size() < 1){
            updateView("An error has occurred while loading languages.");
            System.exit(0);
        }else {
            //Populating the JScrollPane for the languages
            //Gets the name of the img(Flag) from the list
            //Sets the name of the button for the name of the language
            //adds styling to the button
            //adds an actionCommand setting it to the name of the language
            //adds an ActionListener for the set actionCommand
            for(int i = 0; i < languageList.size(); i++){
                String imgFile = config.getProperty("images.flags") +"/"+ languageList.get(i).getImgFile();
                JButton flag = new JButton(languageList.get(i).getLanguageName());
                flag.setIcon(new ImageIcon(new ImageIcon(imgFile).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
                view.getLanguageInnerPanel().add(flag);
                flag.setBackground(Color.white);
                flag.setFocusPainted(false);
                flag.setFocusable(false);
                Border emptyBorder = BorderFactory.createEmptyBorder();
                flag.setBorder(emptyBorder);
                flag.setHorizontalAlignment(SwingConstants.LEFT);
                flag.setHorizontalTextPosition(SwingConstants.CENTER);
                flag.setVerticalTextPosition(SwingConstants.BOTTOM);
                flag.setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
                flag.setActionCommand(languageList.get(i).getLanguageName());
                flag.addActionListener(this);
            }
        }

        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.getDifficultySlider().setMinorTickSpacing(20);
        view.getDifficultySlider().setMajorTickSpacing(100);
        view.getDifficultySlider().setPaintTicks(true);

        //Retrieves from the model a list of difficulty level stored on the database
        List<String> difficultyLabel = model.getDifficulty();

        final int STARTING_POINT = 0;
        final int INCREASE_SLIDER_BY = 20;

        //Initializes the slider to zero
        int sliderValue = STARTING_POINT;
        for(String difficulty : difficultyLabel){
            //SliderSubtitle Hashmap to store the value (Integer) to the Label (Difficulty)
            sliderSubtitle.put(sliderValue, new JLabel(difficulty));
            //setIntervals of the slider to 20.
            sliderValue += 20;
        }
        view.getDifficultySlider().setValue(STARTING_POINT);
        view.getDifficultySlider().setLabelTable(sliderSubtitle);
        view.getDifficultySlider().createStandardLabels(INCREASE_SLIDER_BY);
        view.getDifficultySlider().setPaintLabels(true);

        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
    //Setting the View size
    //Size retrieved from the config.property
    private void setViewSize() {
        view.setPreferredSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMinimumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
        view.setMaximumSize(new Dimension(Integer.parseInt(config.getProperty("window.width")),
                Integer.parseInt(config.getProperty("window.height"))));
    }

    //Initializes controller
    //ActionListener to when clicked on the continueButton calls getViewInfo()
    private void initController(){
        view.getContinueButton().addActionListener( e -> getViewInfo());
    }

    //Method to retrieve the selected language (from the model) and the difficulty
    private void getViewInfo(){
        if(model.getLanguageName() != null){
            int getDifficultyID = view.getDifficultySlider().getValue();
            String difficulty = sliderSubtitle.get(getDifficultyID).getText();
            if(model.verifyIfLanguageExists()){
                appController.setCurrentDifficulty(difficulty);
                appController.setCurrentIdLanguage(model.getLanguageIdByName(model.getLanguageName()));
                appController.navigateToHomePage();
                view.dispose();
            }else{
                updateView("Language selected does not have any content.\nPlease, select a different language.");
            }
        }else{
            updateView("Please, select a language!");
        }
    }

    //Method to retrieve the selected language
    //Uses setLanguageName to store the language on the model
    public void actionPerformed(ActionEvent e) {
        String flagOption = e.getActionCommand();
        for(int i = 0; i < languageList.size(); i++){
            if(flagOption.equals(languageList.get(i).getLanguageName())){
                model.setLanguageName(languageList.get(i).getLanguageName());
                updateView("You have chosen " + model.getLanguageName());
                break;
            }
        }
    }
    //Calls the view printMessage method to display messages
    public void updateView(String message){
        view.printMessage(message);
    }

}