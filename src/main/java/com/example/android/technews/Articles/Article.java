package com.example.android.technews.Articles;

public class Article {

    private String sectionName;

    private String publicationDate;

    private String webTitle;

    private String articleUrl;

    private String author;

    public Article (String secName, String pubDate, String title, String url, String aut) {

        this.sectionName = secName;

        this.publicationDate = pubDate;

        this.webTitle = title;

        this.articleUrl = url;

        this.author = aut;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getAuthor() {
        return author;
    }
}
