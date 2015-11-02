package org.calmarj.sportboxrssreader.utils;

import java.sql.Date;

/**
 * Created by calmarj on 02.11.15.
 */
public class RSSItem {

    private String title;
    private String description;
    private String link;
    private Date pubDate;

    public RSSItem() {
    }

    public RSSItem(String title, String description, String link, Date pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}
