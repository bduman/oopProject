package SimilarityAlgorithms;

import info.debatty.java.stringsimilarity.SorensenDice;

import java.text.NumberFormat;

/**
 *  2 * |V1 inter V2| / (|V1| + |V2|)
 */
public class SorensanDiceSimilarity implements Similarity {

    private double percent = 0;

    public SorensanDiceSimilarity(String str1, String str2) {
        SorensenDice sd = new SorensenDice(2);
        this.percent = sd.similarity(str1, str2);
    }

    @Override
    public double getSimilarityPercent() {
        return this.percent;
    }
}
