package org.calmarj.sportboxrssreader.retrofit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by calmarj on 02.11.15.
 */
@Root(name = "channel", strict = false)
public class Channel {
    @ElementList(name = "item", inline = true)
    List<Item> items;
    @Element
    private String title;
    @Element
    private String link;
    @Element(required = false)
    private String description;

    public List<Item> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}
