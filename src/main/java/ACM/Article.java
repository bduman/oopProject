package ACM;

import FrequencyAlgorithms.ContentFilter;
import FrequencyAlgorithms.Trigram;
import FrequencyAlgorithms.WordCounter;
import SimilarityAlgorithms.JaccardSimilarity;
import SimilarityAlgorithms.SorensanDiceSimilarity;
import org.bson.Document;

public class Article extends Document {

    private Trigram trigram = null;
    private ContentFilter contentFilter = null;
    private String wordCountList = null;
    private String trigramCountList = null;

    Article (int id, String title, String authors, String venue, int year) {
        this.put("id", id);
        this.put("title", title);
        this.put("authors", authors);
        this.put("venue", venue);
        this.put("year", year);
    }

    public int getId() {
        return this.getInteger("id");
    }

    public String getTitle() {
        return this.getString("title");
    }

    public String getAuthors() {
        return this.getString("authors");
    }

    public String getVenue() {
        return this.getString("venue");
    }

    public int getYear() {
        return this.getInteger("year");
    }

    public String getContent() {
        return this.getString("content");
    }

    public void setContent(String content) {
        this.put("content", content);
    }
    /* +++Mediator Pattern+++ */
    private ContentFilter getContentFilter() {
        if (this.contentFilter == null)
            this.contentFilter = new ContentFilter(this.getContent());
        return this.contentFilter;
    }

    private Trigram getTrigram() {
        if (this.trigram == null)
            this.trigram = new Trigram(this.getContentFilter().getFilteredContent());
        return this.trigram;
    }

    public String getWordCountList() {
        if (this.wordCountList == null)
            this.wordCountList = new WordCounter(this.getContentFilter().getFilteredContent()).toString();
        return this.wordCountList;
    }

    public String getTrigramCountList() {
        if (this.trigramCountList == null)
            this.trigramCountList = new WordCounter(this.getTrigram().getWordString()).toString();
        return this.trigramCountList;
    }

    public double getJaccardSimilarityPercent(Article other) {
        return (new JaccardSimilarity(this.getTrigramCountList() + this.getWordCountList(), other.getTrigramCountList()  + other.getWordCountList())).getSimilarityPercent();
    }

    public double getSorensanDiceSimilarityPercent(Article other) {
        return (new SorensanDiceSimilarity(this.getTrigramCountList() + this.getWordCountList(), other.getTrigramCountList() + other.getWordCountList())).getSimilarityPercent();
    }
    /* ---Mediator Pattern--- */
    @Override
    public String toString() {
        return this.getInteger("id") + " | " + this.getString("title") + " | " +
                this.getString("authors") + " | " + this.getString("venue") + " | " +
                this.getInteger("year") + " | " + this.getInteger("id") + ".pdf";
    }
}
