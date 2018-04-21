package com.hueyjj.denzoku.parser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NyaaParser {

    final String TAG = "NyaaParser";
    private static final String ns = null;

    public NyaaParser() {
    }

    public List<NyaaResult> parse(String in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new ByteArrayInputStream(in.getBytes()), null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
        }
    }

    private List<NyaaResult> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<NyaaResult> entries = new ArrayList();

        // Find channel tag (child of rss)
        while (parser.getEventType() == XmlPullParser.START_TAG &&
                !parser.getName().equals("channel")) {
            parser.nextTag();
        }

        // TODO Error checking if we cannot find the channel tag

        // Start searching from channel tag
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                entries.add(readNyaaResult(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private NyaaResult readNyaaResult(XmlPullParser parser) throws XmlPullParserException, IOException {
        String title = null;
        String torrentLink = null;
        String guid = null;
        String pubDate = null;
        String seeders = null;
        String leechers = null;
        String downloads = null;
        String infoHash = null;
        String categoryId = null;
        String category = null;
        String size = null;
        String description = null;

        parser.require(XmlPullParser.START_TAG, ns, "item");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTag(parser, name);
            } else if (name.equals("link")) {
                torrentLink = readTag(parser, name);
            } else if (name.equals("guid")) {
                guid = readTag(parser, name);
            } else if (name.equals("pubDate")) {
                pubDate = readTag(parser, name);
            } else if (name.equals("nyaa:seeders")) {
                seeders = readTag(parser, name);
            } else if (name.equals("nyaa:leechers")) {
                leechers = readTag(parser, name);
            } else if (name.equals("nyaa:downloads")) {
                downloads = readTag(parser, name);
            } else if (name.equals("nyaa:infoHash")) {
                infoHash = readTag(parser, name);
            } else if (name.equals("nyaa:categoryId")) {
                categoryId = readTag(parser, name);
            } else if (name.equals("nyaa:category")) {
                category = readTag(parser, name);
            } else if (name.equals("nyaa:size")) {
                size = readTag(parser, name);
            } else if (name.equals("description")) {
                description = readTag(parser, name);
            } else {
                skip(parser);
            }
        }
        return new NyaaResult(
                title,
                torrentLink,
                guid,
                pubDate,
                seeders,
                leechers,
                downloads,
                infoHash,
                categoryId,
                category,
                size,
                description
        );
    }

    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
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
}
