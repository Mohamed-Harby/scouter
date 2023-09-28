package com.scouter.crawler;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class NaiveCrawler {
    private static CloseableHttpClient httpClient;

    public NaiveCrawler() {
        try {
            this.httpClient = HttpClients.createMinimal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
    public static CrawlResult crawl(String url) {
        HttpGet request = new HttpGet(url);

        String content = null;

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                System.out.println("Can't access the webpage: (Status Code: " + statusCode + ")");
            }
        } catch (Exception e) {
            System.out.println("HTTP Response Exception: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            String filePath = HTMLFileSaver.save(content);
            return new CrawlResult(true, url, null, filePath);
        } catch (Exception e) {
            System.out.println("Can't save the HTML file!");
        }

        return new CrawlResult(false, url, null, "");
    }
}
