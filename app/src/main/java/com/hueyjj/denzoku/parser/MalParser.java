package com.hueyjj.denzoku.parser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MalParser {

    final String TAG = "MalParser";
    private static final String ns = null;

    public MalParser() {
    }

    public List<MalEntry> parse(String in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new ByteArrayInputStream(in.getBytes()), null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
        }
    }

    private List<MalEntry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<MalEntry> entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "myanimelist");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("anime")) {
                entries.add(readMalEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private MalEntry readMalEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        String seriesAnimedbId = null;
        String seriesTitle = null;
        String seriesSynonyms = null;
        String seriesType = null;
        String seriesEpisodes = null;
        String seriesStatus = null;
        String seriesStart = null;
        String seriesEnd = null;
        String seriesImage = null;

        parser.require(XmlPullParser.START_TAG, ns, "anime");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("series_animedb_id")) {
                seriesAnimedbId = readAnimedbId(parser);
            } else if (name.equals("series_title")) {
                seriesTitle = readSeriesTitle(parser);
            } else if (name.equals("series_synonyms")) {
                seriesSynonyms = readSeriesSynonyms(parser);
            } else if (name.equals("series_type")) {
                seriesType = readSeriesType(parser);
            } else if (name.equals("series_episodes")) {
                seriesEpisodes = readSeriesEpisodes(parser);
            } else if (name.equals("series_status")) {
                seriesStatus = readSeriesStatus(parser);
            } else if (name.equals("series_start")) {
                seriesStart = readSeriesStart(parser);
            } else if (name.equals("series_end")) {
                seriesEnd = readSeriesEnd(parser);
            } else if (name.equals("series_image")) {
                seriesImage = readSeriesImage(parser);
            } else {
                skip(parser);
            }
        }
        return new MalEntry(
                seriesAnimedbId,
                seriesTitle,
                seriesSynonyms,
                seriesType,
                seriesEpisodes,
                seriesStatus,
                seriesStart,
                seriesEnd,
                seriesImage
        );
    }

    // Processes series anime db id tags in the rss.
    private String readAnimedbId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_animedb_id");
        String seriesAnimedbId = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_animedb_id");
        return seriesAnimedbId;
    }

    // Processes series title tags in the rss.
    private String readSeriesTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_title");
        String seriesTitle = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_title");
        return seriesTitle;
    }

    // Processes series synonyms tags in the rss.
    private String readSeriesSynonyms(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_synonyms");
        String seriesSynonyms = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_synonyms");
        return seriesSynonyms;
    }

    // Processes series type tags in the rss.
    private String readSeriesType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_type");
        String seriesType = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_type");
        return seriesType;
    }

    // Processes series episodes tags in the rss.
    private String readSeriesEpisodes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_episodes");
        String seriesEpisodes = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_episodes");
        return seriesEpisodes;
    }

    // Processes series status tags in the rss.
    private String readSeriesStatus(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_status");
        String seriesStatus = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_status");
        return seriesStatus;
    }

    // Processes series start tags in the rss.
    private String readSeriesStart(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_start");
        String seriesStart = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_start");
        return seriesStart;
    }

    // Processes series end tags in the rss.
    private String readSeriesEnd(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_end");
        String seriesEnd = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_end");
        return seriesEnd;
    }

    // Processes series image tags in the rss.
    private String readSeriesImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "series_image");
        String seriesImage = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "series_image");
        return seriesImage;
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
