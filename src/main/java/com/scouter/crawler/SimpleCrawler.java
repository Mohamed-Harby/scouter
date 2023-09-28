package com.scouter.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class SimpleCrawler {
    //    @Override
    public static CrawlResult crawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            String htmlContent = document.outerHtml();
            String filePath = HTMLFileSaver.save(htmlContent);
            return new CrawlResult(true, url, document, filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Set<CrawlResult> breadthCrawl(Set<CrawlResult> oldCandidates) {
        Queue<CrawlResult> queue = new ArrayDeque<>(oldCandidates);

        Set<CrawlResult> newCandidates = new HashSet<>();

        int cnt = oldCandidates.size();

        while (!queue.isEmpty() && cnt < 10) {
            CrawlResult crawlResult = queue.poll();
            Set<String> links = LinksExtractor.extract(crawlResult.getDocument());

            for (String link : links) {
                if (cnt >= 10) break;
                CrawlResult childCrawlResult = crawl(link);
                newCandidates.add(childCrawlResult);
                queue.add(childCrawlResult);
                cnt += 1;
            }
        }
        return newCandidates;
    }
}
