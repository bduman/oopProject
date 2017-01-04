package FrequencyAlgorithms;

public class ContentFilter {

    private String content;
    private String filteredContent;

    public ContentFilter(String content) {
        this.content = content;
        this.filteredContent = content.toLowerCase();
        this.removeSpecialChars().removeUrls();
    }

    private ContentFilter removeUrls() {
        this.filteredContent = this.filteredContent.replaceAll("https?://\\S+\\s?", "");
        return this;
    }

    private ContentFilter removeSpecialChars() {
        this.filteredContent = this.filteredContent.replaceAll("[!?,.[\r\n]-()]", "");
        return this;
    }

    public String[] getFilteredContentArray() {
        return this.filteredContent.trim().split("\\s+");
    }

    public String getFilteredContent() {
        return this.filteredContent.trim();
    }
}
