package com.scouter.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SeedsManager {
    private static final String path = "src/main/resources/seeds.txt";

    public static Set<String> getSeeds() {
        Set<String> seeds = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                seeds.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return seeds;
    }
}
