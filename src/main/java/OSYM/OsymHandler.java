package OSYM;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OsymHandler {

    private static final String takvim = "http://www.osym.gov.tr/TR,8797/takvim.html";
    private static final String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

    List<Sinav> sinavlar = new ArrayList<Sinav>();

    public OsymHandler() {

        try {
            Document html = Jsoup.connect(takvim).userAgent(agent).get();
            Elements rows = html.select("div.table > div:not(#pnlSinavTakvimiBaslik)");

            for (Element row : rows) {
                Elements coloumns = row.select("div > div");
                sinavlar.add(new Sinav(coloumns.get(0).text(), coloumns.get(2).text()));
            }

        } catch (IOException e) {
            System.err.println("Osym takvim sitesine baglanamadi.");
        }
    }

    public List<Sinav> getAllSinavlar() {
        return this.sinavlar;
    }
}
