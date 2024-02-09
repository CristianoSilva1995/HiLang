package View;

import javax.swing.*;
import java.util.ArrayList;

/**
 * View for Chat History page
 * @author Shiyan Bhandari, Arkadiusz Nowak
 */

public class ChatHistoryView extends JFrame {
    private JPanel MainPanel;
    private JList chatList;
    private JLabel imgFlag;
    private JLabel lblSubHeader;
    private JLabel lblPageTitle;
    private JPanel panelTop;
    private JButton btnHome;
    private JButton btnProgress;
    private JButton btnLearning;
    private JButton btnChats;
    private JPanel panelMiddle;
    private JFrame frame;
    private JOptionPane dialog;
    private ArrayList<String> historyArrayList = new ArrayList<>();
    private DefaultListModel<String> listModel;

    public ChatHistoryView() {
        // Disallow resizing of the window
        setResizable(false);
        // Exit app on window close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add main panel
        //add(panelMain);
        setContentPane(MainPanel);

        // Show window
        pack();
        setVisible(true);

        listModel = new DefaultListModel();
        chatList.setModel(listModel);
        populateList();
    }

    private void populateList(){
        for(int i = 0; i < historyArrayList.size(); i++){
            listModel.addElement(historyArrayList.get(i));
        }
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        MainPanel = mainPanel;
    }

    public JList getChatList() {
        return chatList;
    }

    public void setChatList(JList chatList) {
        this.chatList = chatList;
    }

    public JLabel getImgFlag() {
        return imgFlag;
    }

    public void setImgFlag(JLabel imgFlag) {
        this.imgFlag = imgFlag;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public ArrayList<String> getHistoryArrayList() {
        return historyArrayList;
    }

    public void setHistoryArrayList(ArrayList<String> historyArrayList) {
        this.historyArrayList = historyArrayList;
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    public JOptionPane getDialog() {
        return dialog;
    }

    public void setDialog(JOptionPane dialog) {
        this.dialog = dialog;
    }

    public JLabel getLblSubHeader() {
        return lblSubHeader;
    }

    public void setLblSubHeader(JLabel lblSubHeader) {
        this.lblSubHeader = lblSubHeader;
    }

    public JLabel getLblPageTitle() {
        return lblPageTitle;
    }

    public void setLblPageTitle(JLabel lblPageTitle) {
        this.lblPageTitle = lblPageTitle;
    }

    public JPanel getPanelTop() {
        return panelTop;
    }

    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }

    public JButton getBtnHome() {
        return btnHome;
    }

    public void setBtnHome(JButton btnHome) {
        this.btnHome = btnHome;
    }

    public JButton getBtnProgress() {
        return btnProgress;
    }

    public void setBtnProgress(JButton btnProgress) {
        this.btnProgress = btnProgress;
    }

    public JButton getBtnLearning() {
        return btnLearning;
    }

    public void setBtnLearning(JButton btnLearning) {
        this.btnLearning = btnLearning;
    }

    public JButton getBtnChats() {
        return btnChats;
    }

    public void setBtnChats(JButton btnChats) {
        this.btnChats = btnChats;
    }

    public JPanel getPanelMiddle() {
        return panelMiddle;
    }

    public void setPanelMiddle(JPanel panelMiddle) {
        this.panelMiddle = panelMiddle;
    }
}