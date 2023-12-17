package com.scouter;

import com.scouter.crawler.CrawlResult;
import com.scouter.crawler.Crawler;
import com.scouter.crawler.RobotsExclusionProtocolChecker;
import com.scouter.crawler.SeedsManager;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;


public class Main {

    static List<String> seeds;



    public static void startCrawling() {
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


    public static void testRobotsExclusionProtocolChecker() throws MalformedURLException {
        RobotsExclusionProtocolChecker robotsExclusionProtocolChecker = new RobotsExclusionProtocolChecker();
        ArrayList<Pattern> patterns = robotsExclusionProtocolChecker.getDisallowedPatterns(
                robotsExclusionProtocolChecker.fetchRobotsTxt(
                        robotsExclusionProtocolChecker.getRobotsTxtUrl("https://www.quora.com/")
                )
        );
        for (Pattern pattern : patterns) {
            System.out.println(pattern);
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        testRobotsExclusionProtocolChecker();
    }
}