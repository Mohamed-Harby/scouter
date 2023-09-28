package com.scouter.crawler;

import java.io.FileWriter;
import java.io.IOException;

public class HTMLFileSaver {
    static final String saveFolderPath = "src/main/resources/crawled-html";
    static int fileIdx = 0;

    public static String save(String content) {
        if (content == null) throw new IllegalArgumentException("content can't be null!");

        String filePath = saveFolderPath + "/" + "HTML-" + fileIdx + ".html";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.out.println("Failed to save HTML content to file: " + e.getMessage());
            e.printStackTrace();
        }
        fileIdx += 1;
        return filePath;
    }
}
