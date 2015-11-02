package org.calmarj.sportboxrssreader.retrofit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by calmarj on 02.11.15.
 */
@Root(name = "item", strict = false)
public class Item {
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private String link;
    @Element(required = false)
    private String author;
    @Element(required = false)
    private String pubDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getPubDate() {
        return pubDate;
    }
}
