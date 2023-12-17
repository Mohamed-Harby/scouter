package com.scouter;

import com.scouter.crawler.CrawlResult;
import com.scouter.crawler.Crawler;
import com.scouter.crawler.SeedsManager;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;


public class Main {

    static List<String> seeds;

    public static void main(String[] args) {

        seeds = new ArrayList<>();

        Set<String> seeds = SeedsManager.getSeeds();

        Crawler crawler = new Crawler();
        Set<CrawlResult> candidates = new HashSet<>();
        for (String url : seeds) {
            CrawlResult candidate = crawler.crawl(url);
            candidates.add(candidate);
        }

        crawler.breadthCrawl(candidates);
    }
}