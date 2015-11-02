package org.calmarj.sportboxrssreader.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calmarj on 02.11.15.
 */
public class RSSParser {

    private static final String ns = null;


    private static final String CHANNEL_TAG = "channel";

    private static final String ITEM_TAG = "item";
    private static final String TITLE_TAG = "title";
    private static final String DESCRIPTION_TAG = "description";
    private static final String LINK_TAG = "link";
    private static final String PUBDATE_TAG = "pubDate";


    public RSSChannel parse(InputStream in) throws IOException, XmlPullParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parser.nextTag();
            return readChannel(parser);
        } finally {
            in.close();
        }
    }

    private RSSChannel readChannel(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<RSSItem> items = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, null, CHANNEL_TAG);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(ITEM_TAG)) {
                RSSItem item = readItem(parser);
                Log.i("LOL", item.getTitle());
                items.add(item);
            } else {
                skip(parser);
            }
        }
        RSSChannel channel = new RSSChannel();
        channel.setItems(items);
        return channel;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private RSSItem readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, ITEM_TAG);
        String title = null;
        String description = null;
        String link = null;
        Date pubDate = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TITLE_TAG)) {
                title = readTitle(parser);
            } else if (name.equals(DESCRIPTION_TAG)) {
                description = readDescription(parser);
            } else if (name.equals(LINK_TAG)) {
                link = readTag(parser, LINK_TAG);
            } else if (name.equals(PUBDATE_TAG)) {
                pubDate = readPubDate(parser);
            } else {
                skip(parser);
            }
        }
        return new RSSItem(title, description, link, pubDate);


    }

    private Date readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        String dateString = readTag(parser, PUBDATE_TAG);
        Date date = new Date(1);
        return date;
    }

    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, DESCRIPTION_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, DESCRIPTION_TAG);
        return title;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TITLE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TITLE_TAG);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
