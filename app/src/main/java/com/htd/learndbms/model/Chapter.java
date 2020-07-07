package com.htd.learndbms.model;


public class Chapter {
    private String name ;
    private String url;
    private String id;
    private boolean header;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public Chapter(String name, boolean header, String url) {
        this.name = name;
        this.header = header;
        this.url = url;
    }

    public Chapter(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }
}
