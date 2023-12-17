package com.scouter.crawler;

import org.jsoup.nodes.Document;

public record CrawlResult(boolean successful, String url, Document document, String filePath) {

    @Override
    public String toString() {
        return "CrawlResult {" + "\n" +
                "\tsuccessful: " + successful + ",\n" +
                "\tfilePath: " + filePath + ",\n" +
                "\turl: " + url + "\n" +
                '}';
    }
}
