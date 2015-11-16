package org.calmarj.sportboxrssreader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item implements Comparable<Item> {
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

    public Item() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public int compareTo(Item another) {
        return pubDate.compareTo(another.getPubDate());
    }
}
