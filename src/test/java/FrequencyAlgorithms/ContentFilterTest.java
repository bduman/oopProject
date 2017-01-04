package FrequencyAlgorithms;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentFilterTest {
    @Test
    public void getFilteredContent() throws Exception {
        String testStr = "qwerty http://www.google.com ! ? ,.....-()";
        ContentFilter contentFilter = new ContentFilter(testStr);
        assertEquals("qwerty", contentFilter.getFilteredContent());
    }

}