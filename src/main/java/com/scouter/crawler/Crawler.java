package com.scouter.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Crawler {

    static final int MAX_NUMBER_OF_CANDIDATES = 5000;

    public static CrawlResult crawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            String htmlContent = document.outerHtml();
            String filePath = HTMLFileSaver.save(htmlContent);
            return new CrawlResult(true, url, document, filePath);

        } catch (Exception e) {
//            throw new RuntimeException(e);
            return new CrawlResult(false, url, null, null);
        }
    }


    public static void breadthCrawl(Set<CrawlResult> candidates) {
        Queue<CrawlResult> queue = new ArrayDeque<>(candidates);
        int cntOldSeeds = candidates.size();

        System.out.println("Crawling...");
        while (!queue.isEmpty() && candidates.size() < MAX_NUMBER_OF_CANDIDATES) {
            CrawlResult crawlResult = queue.poll();

            if (!crawlResult.isSuccessful()) continue;

            Set<String> links = LinksExtractor.extract(crawlResult.getDocument());

            for (String link : links) {
                if (candidates.size() >= MAX_NUMBER_OF_CANDIDATES) break;
                CrawlResult childCrawlResult = crawl(link);
                candidates.add(childCrawlResult);
                queue.add(childCrawlResult);
            }
        }
        int cntNewCandidates = candidates.size() - cntOldSeeds;
        System.out.println("Crawled " + cntNewCandidates + " files successfully.");
    }
}
