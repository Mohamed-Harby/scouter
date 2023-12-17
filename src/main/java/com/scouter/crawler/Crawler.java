package com.scouter.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class Crawler {

    static final int MAX_NUMBER_OF_CANDIDATES = 30;
    private final HTMLFileSaver htmlFileSaver = new HTMLFileSaver();
    public CrawlResult crawl(String url) {
        try {
            RobotsExclusionProtocolChecker repChecker = new RobotsExclusionProtocolChecker();
            if (!repChecker.isUrlAllowed(url)) {
                return new CrawlResult(false, url, null, null);
            }

            Document document = Jsoup.connect(url).get();
            String filePath = htmlFileSaver.save(document);
            return new CrawlResult(true, url, document, filePath);
        } catch (IOException e) {
            return new CrawlResult(false, url, null, null);
        }
    }


    public void breadthCrawl(Set<CrawlResult> candidates) {
        Queue<CrawlResult> queue = new ArrayDeque<>(candidates);
        int cntOldSeeds = candidates.size();

        System.out.println("Crawling...");
        while (!queue.isEmpty() && candidates.size() < MAX_NUMBER_OF_CANDIDATES) {
            CrawlResult crawlResult = queue.poll();

            if (!crawlResult.successful()) continue;

            Set<String> links = LinksExtractor.extract(crawlResult.document());

            for (String link : links) {
                if (candidates.size() >= MAX_NUMBER_OF_CANDIDATES) break;
                CrawlResult childCrawlResult = this.crawl(link);
                candidates.add(childCrawlResult);
                queue.add(childCrawlResult);
            }
        }
        int cntNewCandidates = candidates.size() - cntOldSeeds;
        System.out.println("Crawled " + cntNewCandidates + " files successfully.");
    }
    
    public void depthCrawl(CrawlResult rootCandidate) {
        // TODO: Implement the code here.
    }
}