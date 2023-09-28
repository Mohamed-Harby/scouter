package com.scouter.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class LinksExtractor {

    public static Set<String> extract(Document document) {
        Set<String> links = new HashSet<>();
        Elements linksElements = document.select("a[href]");
        for (Element link : linksElements) links.add(link.attr("abs:href"));

        return links;
    }
}
