package com.hueyjj.denzoku.parser;

public class NyaaQueryBuilder {

    private String query;
    private NyaaQuery.Sort sort = NyaaQuery.Sort.SEEDERS;
    private String page = "rss";
    private String pageNum = "0";
    private String[] tags = new String[]{"HorribleSubs", "480p"};

    public NyaaQueryBuilder() {
    }

    public NyaaQuery buildNyaaQuery() {
        return new NyaaQuery(query, sort, page, pageNum, tags);
    }

    public NyaaQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public NyaaQueryBuilder sort(NyaaQuery.Sort sort) {
        this.sort = sort;
        return this;
    }

    public NyaaQueryBuilder page(String page) {
        this.page = page;
        return this;
    }

    public NyaaQueryBuilder pageNum(String pageNum) {
        this.pageNum = pageNum;
        return this;
    }
}
