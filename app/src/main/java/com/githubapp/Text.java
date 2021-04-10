package com.githubapp;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

public class Text extends LitePalSupport implements Serializable {
    private String name;
    private String cover;
    private String description;
    private String clickTotal;
    private String author;

    public Text(String name, String cover, String description, String clickTotal, String author) {
        this.name = name;
        this.cover = cover;
        this.description = description;
        this.clickTotal = clickTotal;
        this.author = author;
    }

    public Text(String name, String cover, String description, String author) {
        this.name = name;
        this.cover = cover;
        this.description = description;
        this.author = author;
    }

    public Text() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClickTotal() {
        return clickTotal;
    }

    public void setClickTotal(String clickTotal) {
        this.clickTotal = clickTotal;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
