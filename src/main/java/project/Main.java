package project;

import ACM.Article;
import ACM.Mongo;
import ACM.SimilarityAnalyzer;
import OSYM.OsymHandler;
import OSYM.Sinav;
import org.apache.commons.lang3.text.StrSubstitutor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;

public class Main extends Form {

    private static final String detailsTemplate = "<html>En çok tekrar eden 10 kelime <br> ${fwords} " +
            "<br> 3-Gram listesinde en çok tekrar eden 10 kelime <br> ${trigram} <br> " +
            "<br> Jaccard Benzerliğine göre <br> ${jaccard} <br>" +
            "<br> Sorensan Dice Benzerliğine göre <br> ${sorensan} <br>";

    public static void main(String[] args) {

        final Mongo database = new Mongo();
        final OsymHandler osymHandler = new OsymHandler();

        /*AcmWrapper acm = new AcmWrapper("acm.csv", 10);
        List<ACM> articles = acm.getAll();

        for (ACM article : articles){
            database.insertArticle(article);
        }

        ACM currentArticle = null;
        try {
            currentArticle = csvReader.get(0);
        } catch (Exception e) {
            System.out.println("Cant get article from list.");
        }

        //database.insertArticle(currentArticle); INSERT
        //currentArticle = database.getArticle(304586); GET SINGLE DOCUMENT
        //database.getAllArticles(); GET ALL DOCUMENT FROM article
        //database.deleteArticle(currentArticle); DELETE DOCUMENT
        //database.updateArticle(currentArticle); UPDATE DOCUMENT
        */
        JFrame frame = new JFrame("Form");
        final Form form = new Form();
        frame.setContentPane(form.getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setVisible(true);
        // Sinav eklemesi
        form.setSinavlar(osymHandler.getAllSinavlar());

        form.searchButtonEvent(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                form.clearAllArticles();
                switch (form.getSelectedIndex()) {
                    case 1:
                        int articleId = Integer.parseInt(form.getSearchText());
                        Article singleDocument = database.getArticle(articleId);
                        form.addArticle(singleDocument);
                        break;
                    case 2:
                        int year = Integer.parseInt(form.getSearchText());
                        List<Article> multiDocument = database.getArticleByYear(year);
                        for (Article document : multiDocument) {
                            form.addArticle(document);
                        }
                        break;
                    }
            }
        });

        form.sinavlarItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Sinav sinav = (Sinav) e.getItem();
                form.changeOsymSinavTarihiLabel(sinav.getSinavTarihi());
            }
        });

        form.articlesComboBoxItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int articleId = form.getSelectedComboBoxItemId();
                Article singleDocument = database.getArticle(articleId);
                String resolvedString;

                try {
                    String fwords = singleDocument.getWordCountList();
                    String trigram = singleDocument.getTrigramCountList();
                    SimilarityAnalyzer analyzer = new SimilarityAnalyzer(singleDocument, database.getAllArticles());
                    HashMap<String, String> mustacheMap = new HashMap<String, String>();

                    mustacheMap.put("fwords", fwords);
                    mustacheMap.put("trigram", trigram);
                    mustacheMap.put("jaccard", analyzer.getAllJaccardPercents());
                    mustacheMap.put("sorensan", analyzer.getAllSorensanDicePercents());

                    StrSubstitutor sub = new StrSubstitutor(mustacheMap);
                    resolvedString = sub.replace(detailsTemplate);
                } catch (NullPointerException exception) {
                    resolvedString = "...";
                }

                form.changeArticleDetailsLabel(resolvedString);
            }
        });

    }


}
