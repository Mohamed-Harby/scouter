package com.scouter.crawler;

import org.jsoup.nodes.Document;

public class CrawlResult {
    private final boolean successful;
    private final String filePath;
    private final String url;

    private final Document document;

    public CrawlResult(boolean successful, String url, Document document, String filePath) {
        this.successful = successful;
        this.url = url;
        this.document = document;
        this.filePath = filePath;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUrl() {
        return url;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public String toString() {
        return "CrawlResult {" + "\n" +
                "\tsuccessful: " + successful + ",\n" +
                "\tfilePath: " + filePath + ",\n" +
                "\turl: " + url + "\n" +
                '}';
    }
}
