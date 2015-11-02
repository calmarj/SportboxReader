package org.calmarj.sportboxrssreader.utils;

import java.util.List;

/**
 * Created by calmarj on 02.11.15.
 */
public class RSSChannel {

    private String title;
    private String link;
    private String description;
    private String language;

    private List<RSSItem> items;

    public RSSChannel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<RSSItem> getItems() {
        return items;
    }

    public void setItems(List<RSSItem> items) {
        this.items = items;
    }
}
