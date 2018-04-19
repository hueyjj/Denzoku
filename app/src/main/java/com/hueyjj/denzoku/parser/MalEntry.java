package com.hueyjj.denzoku.parser;

public class MalEntry {

    public final String seriesAnimedbId;
    public final String seriesTitle;
    public final String seriesSynonyms;
    public final String seriesType;
    public final String seriesEpisodes;
    public final String seriesStatus;
    public final String seriesStart;
    public final String seriesEnd;
    public final String seriesImage;

    public MalEntry(String seriesAnimedbId,
                    String seriesTitle,
                    String seriesSynonyms,
                    String seriesType,
                    String seriesEpisodes,
                    String seriesStatus,
                    String seriesStart,
                    String seriesEnd,
                    String seriesImage
    ) {
        this.seriesAnimedbId    = seriesAnimedbId;
        this.seriesTitle        = seriesTitle;
        this.seriesSynonyms     = seriesSynonyms;
        this.seriesType         = seriesType;
        this.seriesEpisodes     = seriesEpisodes;
        this.seriesStatus       = seriesStatus;
        this.seriesStart        = seriesStart;
        this.seriesEnd          = seriesEnd;
        this.seriesImage        = seriesImage;
    }

    public String toFormattedString() {
        return "series animedb id: " + this.seriesAnimedbId + "\n"
                + "series title: " + this.seriesTitle + "\n"
                + "series synonyms: " + this.seriesSynonyms + "\n"
                + "series type: " + this.seriesType + "\n"
                + "series episodes: " + this.seriesEpisodes + "\n"
                + "series status: " + this.seriesStatus + "\n"
                + "series start: " + this.seriesStart + "\n"
                + "series end: " + this.seriesEnd + "\n"
                + "series image: " + this.seriesImage + "\n";
    }

    @Override
    public String toString() {
        return this.seriesAnimedbId + ", "
                + this.seriesTitle + ", "
                + this.seriesSynonyms + ", "
                + this.seriesType + ", "
                + this.seriesEpisodes + ", "
                + this.seriesStatus + ", "
                + this.seriesStart + ", "
                + this.seriesEnd + ", "
                + this.seriesImage;
    }
}


