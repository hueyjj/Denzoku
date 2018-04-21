package com.hueyjj.denzoku.parser;

import java.io.Serializable;

public class NyaaResult implements Serializable {

    public final String title;
    public final String torrentLink;
    public final String guid;
    public final String pubDate;
    public final String seeders;
    public final String leechers;
    public final String downloads;
    public final String infoHash;
    public final String categoryId;
    public final String category;
    public final String size;
    public final String description;

    public NyaaResult(String title,
                      String torrentLink,
                      String guid,
                      String pubDate,
                      String seeders,
                      String leechers,
                      String downloads,
                      String infoHash,
                      String categoryId,
                      String category,
                      String size,
                      String description) {
        this.title = title;
        this.torrentLink = torrentLink;
        this.guid = guid;
        this.pubDate = pubDate;
        this.seeders = seeders;
        this.leechers = leechers;
        this.downloads = downloads;
        this.infoHash = infoHash;
        this.categoryId = categoryId;
        this.category = category;
        this.size = size;
        this.description = description;
    }

    public String toFormattedString() {
        return "Title: " + this.title + "\n"
                + "Torrent link: " + this.torrentLink + "\n"
                + "Guid: " + this.guid + "\n"
                + "Pub date: " + this.pubDate + "\n"
                + "Seeders: " + this.seeders + "\n"
                + "Leechers: " + this.leechers + "\n"
                + "Downloads: " + this.downloads + "\n"
                + "Infohash: " + this.infoHash + "\n"
                + "Category ID: " + this.categoryId + "\n"
                + "Category: " + this.category + "\n"
                + "Size: " + this.size + "\n"
                + "Description: " + this.description + "\n";
    }

    @Override
    public String toString() {
        return  this.title + ", "
                + this.torrentLink + ", "
                + this.guid + ", "
                + this.pubDate + ", "
                + this.seeders + ", "
                + this.leechers + ", "
                + this.downloads + ", "
                + this.infoHash + ", "
                + this.categoryId + ", "
                + this.category + ", "
                + this.size + ", "
                + this.description;
    }
}