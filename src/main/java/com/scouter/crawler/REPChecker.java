package com.scouter.crawler;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class REPChecker {

    private String getRobotsTxtUrl(String url) throws MalformedURLException {
        String domain = "";
        try {
            URL parsedUrl = new URL(url);
            domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("URL parsing issue.");
        }
        return domain + "/robots.txt";
    }

    private String fetchRobotsTxt(String robotsTxtUrl) {
        try {
            Document doc = Jsoup.connect(robotsTxtUrl).ignoreContentType(true).get();
            return doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // The robots.txt couldn't be fetched
    }


    public boolean isUrlAllowed(String url) throws IOException {

        String robotsTxtUrl = getRobotsTxtUrl(url);
        String robotsTxtContent = fetchRobotsTxt(robotsTxtUrl);

        if (robotsTxtContent == null || robotsTxtContent.isEmpty()) {
            return true;
        }

        String urlPath = new URL(url).getPath();

        String[] lines = robotsTxtContent.split("\\r?\\n");
        for (String line : lines) {
            if (line.startsWith("Disallow:") &&
                    urlPath.startsWith(line.substring(10).trim())) {
                return false;
            }
        }
        return true;
    }
}
