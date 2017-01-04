package FrequencyAlgorithms;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Trigram {

    private List<String> words = new ArrayList<String>();

    public Trigram(String content) {
        ContentFilter contentFilter = new ContentFilter(content);
        String filteredContent = contentFilter.getFilteredContent();

        StringReader stringReader = new StringReader(filteredContent);
        NGramTokenizer tokenizer = new NGramTokenizer(3, 3);
        String token;

        tokenizer.setReader(stringReader);
        try {
            tokenizer.reset();
            CharTermAttribute termAtt = tokenizer.getAttribute(CharTermAttribute.class);
            while (tokenizer.incrementToken()) {
                token = termAtt.toString();
                if (!token.contains(" "))
                    this.words.add(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWordList() {
        return this.words;
    }

    public String[] getWordArray() {
        return this.words.toArray(new String[0]);
    }

    public String getWordString() {
        return StringUtils.join(this.words.toArray(), " ");
    }
}
