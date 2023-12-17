package com.scouter.crawler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class RobotsExclusionProtocolChecker {

    public boolean isUrlAllowed(String url) throws IOException {

        String robotsTxtContent = fetchRobotsTxt(getRobotsTxtUrl(url));

        if (robotsTxtContent == null || robotsTxtContent.isEmpty()) {
            return true;
        }

        ArrayList<Pattern> disallowedPatterns = getDisallowedPatterns(robotsTxtContent);
        String urlPath = new URL(url).getPath();

        for (Pattern disallowedPattern : disallowedPatterns) {
            if (disallowedPattern.matcher(urlPath).matches()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Pattern> getDisallowedPatterns(String robotsTxtContent) {

        String[] lines = robotsTxtContent.split("\\r?\\n");

        ArrayList<Pattern> disallowedPaths = new ArrayList<>();

        for (int lineIdx = 0; lineIdx < lines.length; ++lineIdx) {
            if (lines[lineIdx].startsWith("User-agent: *")) {
                while (++lineIdx < lines.length) {
                    if (lines[lineIdx].startsWith("Allow:")) {
                        continue;
                    } else if (lines[lineIdx].startsWith("Disallow:")) {
                        disallowedPaths.add(pathToPattern(lines[lineIdx].substring(10).trim()));
                    } else {
                        break;
                    }
                }
                break;
            }
        }
        return disallowedPaths;
    }





    private Pattern pathToPattern(String path) {
        String regexPattern = path.replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", "\\?");
        return Pattern.compile(regexPattern);
    }

    public String getRobotsTxtUrl(String url) throws MalformedURLException {
        String domain = "";
        try {
            URL parsedUrl = new URL(url);
            domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("URL parsing issue.");
        }
        return domain + "/robots.txt";
    }


    public String fetchRobotsTxt(String robotsTxtUrl) {
        try {
            URLConnection connection = new URL(robotsTxtUrl).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
