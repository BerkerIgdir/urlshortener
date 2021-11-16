package com.urlshortener.pojo;


import java.io.Serializable;

public class URLInfo implements Serializable {
    private long id;
    private final String shortUrl;
    private final String longUrl;

    public URLInfo (long id , String shortUrl , String longUrl) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getShortUrl () {
        return shortUrl;
    }

    public String getLongUrl () {
        return longUrl;
    }
}
