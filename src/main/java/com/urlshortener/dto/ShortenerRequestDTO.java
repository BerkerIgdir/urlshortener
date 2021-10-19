package com.urlshortener.dto;


public class ShortenerRequestDTO {

    private String url;

    public ShortenerRequestDTO(){
        //
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url){
        this.url = url;
    }
}
