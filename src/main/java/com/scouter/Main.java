package com.scouter;

import com.scouter.crawler.CrawlResult;
import com.scouter.crawler.Crawler;
import com.scouter.crawler.NaiveCrawler;
import com.scouter.crawler.SimpleCrawler;

import java.io.*;
import java.util.*;

public class Main {

    static List<String> seeds;

    public static void main(String[] args) throws IOException {

        seeds = new ArrayList<>();
//        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/seeds.txt")) {
//            BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
//            var x = inputStream.readAllBytes();
//            System.out.println(x);
//        }

        SimpleCrawler crawler = new SimpleCrawler();
//        crawler.crawl("https://www.springcloud.io/post/2022-08/httpclient5/#gsc.tab=0");

//        crawler.crawl("123");
        CrawlResult crawlResult = crawler.crawl("https://www.github.com/");
        Set<CrawlResult> t = new HashSet<>();
        t.add(crawlResult);
        Set<CrawlResult> res = SimpleCrawler.breadthCrawl(t);

        System.out.println(res);


    }
}