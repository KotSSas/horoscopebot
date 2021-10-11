package categories;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Week {

    public String printText(String c){
        String url = "https://www.elle.ru/astro/" + c + "/week/";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ar = document.getElementsByClass("articleParagraph articleParagraph_dropCap");
        String text = ar.text();

        return text;
    }
}
