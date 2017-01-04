package ACM;
import java.util.ArrayList;
// for csv read
import com.csvreader.CsvReader;
// for get pdf link
import org.jsoup.Connection;
import org.jsoup.Jsoup;
// for download pdf
import java.io.*;
import java.util.List;
import java.util.Map;
// for extract pdf
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class AcmWrapper extends Exception {

    private static final String hostUrl = "http://dl.acm.org/";
    private static final String pageUrl = "citation.cfm?id=";
    private CsvReader reader;
    private ArrayList<Article> articles = new ArrayList<Article>();

    AcmWrapper(String fileName, int limit) {
        try {
            reader = new CsvReader(fileName);
            reader.readHeaders();
            this.readLines(limit);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException. [1]");
        } catch (IOException e) {
            System.err.println("IOException. [1]");
        }

        this.downloadArticles();
    }

    private void downloadArticles() {
        for (Article article : this.articles) {
            try {
                this.downloadArticle(article.getId());
                try {
                    String content = this.getTextFromPdf(article.getId());
                    article.setContent(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readLines(int limit) {
        try {
            int i = 0;
            while (this.reader.readRecord() && i < limit) {
                int id = Integer.parseInt(this.reader.get("id"));
                String title = this.reader.get("title");
                String authors = this.reader.get("authors");
                String venue = this.reader.get("venue");
                int year = Integer.parseInt(this.reader.get("year"));

                Article articleObject = new Article(id, title, authors, venue, year);
                this.articles.add(articleObject);
                i++;
            }
        } catch (IOException e) {
            System.err.println("IOException. [2]");
        }
    }

    private int size() {
        return this.articles.size();
    }

    public List<Article> getAll() {
        return this.articles;
    }

    public Article get(int i) throws Exception {
        if (this.size() > i)
            return this.articles.get(i);
        else
            throw new Exception("OutLimit [1]");
    }

    private boolean download(byte[] document, String name) {
        try {
            InputStream in = new ByteArrayInputStream(document);
            String save = name + ".pdf";
            FileOutputStream fos = new FileOutputStream(new File(save));

            int length = -1;
            byte[] buffer = new byte[1024];

            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            in.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void downloadArticle(int id) throws Exception {
        try {
            String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

            Connection.Response document = Jsoup.connect(AcmWrapper.hostUrl + AcmWrapper.pageUrl + id)
                    .userAgent(agent)
                    .execute();

            Map<String, String> documentCookies = document.cookies();
            String link = AcmWrapper.hostUrl + document.parse().select("a[name='FullTextPDF']").attr("href");

            Connection.Response pdf = Jsoup.connect(link)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent(agent)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .cookies(documentCookies)
                    .execute();

            boolean downloaded = this.download(pdf.bodyAsBytes(), Integer.toString(id));
            if (!downloaded) {
                throw new Exception(id + ": couldnt download.");
            }
        } catch (IOException e) {
            System.err.println("IOException [3]");
        }
    }

    private String getTextFromPdf(int id) throws IOException {
        File pdfFile = new File(Integer.toString(id) + ".pdf");
        PDDocument doc = PDDocument.load(pdfFile);
        String content = new PDFTextStripper().getText(doc);
        doc.close();
        return content;
    }

}
