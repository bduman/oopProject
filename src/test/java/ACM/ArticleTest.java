package ACM;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {
    @Test
    public void getWordCountList() throws Exception {
        Article sample = new Article(1, "Title", "Authors", "Venue", 1994);
        sample.setContent("test content test content test content test content test");
        assertEquals(sample.getWordCountList(), "{test=5, content=4}");
    }

    @Test
    public void getJaccardSimilarityPercent() throws Exception {
        Article sample = new Article(1, "Title", "Authors", "Venue", 1994);
        sample.setContent("test content test content test content test content");
        assertEquals(1, ((int) sample.getJaccardSimilarityPercent(sample)));
    }

    @Test
    public void getSorensanDiceSimilarityPercent() throws Exception {
        Article sample = new Article(1, "Title", "Authors", "Venue", 1994);
        sample.setContent("test content test content test content test content");
        assertEquals(1, ((int) sample.getSorensanDiceSimilarityPercent(sample)));
    }

}