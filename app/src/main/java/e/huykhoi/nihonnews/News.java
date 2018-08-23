package e.huykhoi.nihonnews;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by huykhoi on 13/12/2017.
 */
public class News{
    String title;
    String timePublic;
    String link;

    public News() {
        this.title = title;
        this.timePublic = timePublic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimePublic() {
        return timePublic;
    }

    public void setTimePublic(String timePublic) {
        this.timePublic = timePublic;
    }
}

