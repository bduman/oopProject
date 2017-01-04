package SimilarityAlgorithms;

import info.debatty.java.stringsimilarity.Jaccard;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *   |V1 inter V2| / |V1 union V2|.
 */
public class JaccardSimilarity implements Similarity {

    private double percent = 0;

    public JaccardSimilarity(String str1, String str2) {
        Jaccard j = new Jaccard();
        this.percent = j.similarity(str1, str2);
    }

    @Override
    public double getSimilarityPercent() {
        return this.percent;
    }
}
