package ACM;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

public class SimilarityAnalyzer {

    private Article self;
    private List<Article> allArticles;

    public SimilarityAnalyzer(Article self, List<Article> allArticles) {
        this.allArticles = allArticles;
        this.self = self;
    }

    public String getAllJaccardPercents() {
        HashMap<Integer, String> percentMap = new HashMap<Integer, String>();
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        for (Article other : this.allArticles) {
            if (other.getId() != self.getId()) {
                Double percent = self.getJaccardSimilarityPercent(other);
                percentMap.put(other.getId(), myFormat.format(percent));
            }
        }

        return percentMap.toString();
    }

    public String getAllSorensanDicePercents() {
        HashMap<Integer, String> percentMap = new HashMap<Integer, String>();
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        for (Article other : this.allArticles) {
            if (other.getId() != self.getId()) {
                Double percent = self.getSorensanDiceSimilarityPercent(other);
                percentMap.put(other.getId(), myFormat.format(percent));
            }
        }

        return percentMap.toString();
    }
}
