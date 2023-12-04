package com.flightpriceanalysis.flightpriceanalysis;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class PageRanking {

    // Page class encapsulates information about a web page
    class WebPage {
        String pageName; // The name of the web page
        int score; // Score based on keyword matches

        // Constructor to initialize the web page attributes
        WebPage(String name, int score) {
            this.pageName = name;
            this.score = score;
        }
    }

    // Main method to perform web page ranking
    public void rankWebPages(String inputKeywords) {
        // Directory containing the web pages
        String webPagesDirectory = "src";
        File folder = new File(webPagesDirectory);

        // Get all files from the directory
        File[] listOfFiles = folder.listFiles();

        // List to store input keywords
        List<String> keywords = new ArrayList<>();

        // Process input keywords
        for (String keyword : inputKeywords.split(",")) {
            keywords.add(keyword.trim().toLowerCase());
        }

        // Check if there are any keywords
        if (keywords.isEmpty()) {
            System.out.println("Please enter at least one keyword.");
            return;
        }

        // A Map to store the frequency of each keyword
        Map<String, Integer> keywordFrequencies = new HashMap<>();

        // Initialize keyword frequencies to 0
        for (String keyword : keywords) {
            keywordFrequencies.put(keyword, 0);
        }

        // Priority queue to store web pages based on their scores
        PriorityQueue<WebPage> pageHeap = new PriorityQueue<>(10, Comparator.comparingInt(p -> p.score));

        // Iterate through each web page
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".txt")) {
                int score = calculateScore(file, keywords, keywordFrequencies);
                WebPage page = new WebPage(file.getName(), score);
                pageHeap.offer(page);
            }
        }

        // Print the top 10 web pages based on keyword matches
        System.out.println("Top web pages based on keyword matches:\n");
        List<WebPage> topPages = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            if (pageHeap.isEmpty()) {
                break;
            }
            WebPage page = pageHeap.poll();
            topPages.add(page);
            System.out.println(i + ". " + page.pageName + " - score: " + page.score);
        }
    }

    // Helper method to calculate the score for a web page
    private int calculateScore(File file, List<String> keywords, Map<String, Integer> keywordFrequencies) {
        int score = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken().toLowerCase().replaceAll("[^a-z0-9]+", "");

                    if (keywords.contains(word)) {
                        score += keywordFrequencies.getOrDefault(word, 0) + 1;
                        keywordFrequencies.put(word, keywordFrequencies.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Reset keyword frequencies to 0
            for (String keyword : keywords) {
                keywordFrequencies.put(keyword, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return score;
    }
}

