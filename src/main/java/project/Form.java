package project;

import ACM.Article;
import OSYM.Sinav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class Form extends Container implements ItemListener {
    private JPanel Panel;
    private JPanel searchRadioButtonsPanel;

    private JLabel aramaLabel;

    private JRadioButton yılıRadioButton;
    private JRadioButton makaleIDRadioButton;
    private int selectedIndex = 1;

    private JTextField searchTextField;
    private JButton araButton;
    private JList articleList;
    private JComboBox articlesComboBox;
    private JLabel articleDetails;
    private JComboBox osymComboBox;
    private JLabel osymSinavTarihiLabel;

    private Object columnNames[] = {"ID", "Title", "Authors", "Venue", "Year", "PDF"};

    public Form() {
        yılıRadioButton.addItemListener(this);
        makaleIDRadioButton.addItemListener(this);

        articleList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    int articleId = Integer.parseInt(list.getModel().getElementAt(index).toString().split("\\s+")[0]);
                    try {
                        Runtime.getRuntime().exec("explorer.exe /open, " + articleId + ".pdf");
                    } catch (IOException e1) {
                        System.out.println("Dosyayı açamadım.");
                    }
                }
            }
        });
    }

    void setSinavlar(java.util.List<Sinav> sinavlar) {
        for (Sinav sinav : sinavlar) {
            osymComboBox.addItem(sinav);
        }
    }

    JPanel getMainPanel() {
        return this.Panel;
    }

    String getSearchText() {
        return this.searchTextField.getText();
    }

    int getSelectedIndex() {
        return this.selectedIndex;
    }

    void searchButtonEvent(ActionListener l) {
        araButton.addActionListener(l);
    }

    int getSelectedComboBoxItemId() {
        try {
            return ((ComboItem) this.articlesComboBox.getSelectedItem()).getValue();
        } catch (Exception e) {
            return 0;
        }
    }

    void sinavlarItemListener(ItemListener l) {
        osymComboBox.addItemListener(l);
    }


    void articlesComboBoxItemListener(ItemListener l) {
        articlesComboBox.addItemListener(l);
    }

    void changeArticleDetailsLabel(String details) {
        articleDetails.setText(String.valueOf(details));
    }

    void changeOsymSinavTarihiLabel(String tarih) {
        osymSinavTarihiLabel.setText(String.valueOf(tarih));
    }

    void clearAllArticles() {
        DefaultListModel listModel = (DefaultListModel) articleList.getModel();
        listModel.removeAllElements();
        articlesComboBox.removeAllItems();
    }

    void addArticle(Article article) {
        DefaultListModel listModel = (DefaultListModel) articleList.getModel();
        listModel.addElement(article);
        articlesComboBox.addItem(new ComboItem(article.getTitle(), article.getId()));
    }

    public void itemStateChanged(ItemEvent e) {

        Object source = e.getItemSelectable();
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (source == makaleIDRadioButton) {
                this.selectedIndex = 1;
            } else if (source == yılıRadioButton) {
                this.selectedIndex = 2;
            }
        }
    }
}
