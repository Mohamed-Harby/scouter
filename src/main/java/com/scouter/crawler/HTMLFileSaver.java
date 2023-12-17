package com.scouter.crawler;

import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;

public class HTMLFileSaver {
    static final String crawledPagesFolderPath = "src/main/resources/crawled-html";
    static int fileIdx = 0;

    public String save(Document document) {
        if (document == null) throw new IllegalArgumentException("document can't be null!");

        String htmlContent = document.outerHtml();

        String filePath = crawledPagesFolderPath + "/" + "HTML-" + fileIdx + ".html";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(htmlContent);
        } catch (IOException e) {
            System.out.println("Failed to save HTML content to file: " + e.getMessage());
            e.printStackTrace();
        }
        fileIdx += 1;
        return filePath;
    }

    public String getHash(Document document) {
        // TODO: get unique name to the document depending on its content.
        // NOTE: the hash should be the same for the same file if it's hashed again.
        // You can utilize `document.ownText()` to get text without html tags.

        return "text-without-html-tags";
    }
}
