package com.flightpriceanalysis.flightpriceanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FrequencyCount {
    public static void count() {

        String filepath = "src";
        try {
            Map<String, Integer> wordFrequencyMap = getWordFrequency(filepath);

            System.out.println("Word Frequency Count in the File:");
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static Map<String, Integer> getWordFrequency(String filePath) throws IOException {

        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for(File file: listOfFiles) {

            if (file.getName().endsWith(".txt")) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                // Split the content into words using regular expression
                String[] words = content.toString().split("\\s+");

                // Count the frequency of each word
                for (String word : words) {
                    wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                }

                reader.close();
            }
        }
        return wordFrequencyMap;
    }
}