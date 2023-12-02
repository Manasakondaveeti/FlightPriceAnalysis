package com.flightpriceanalysis.flightpriceanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class SearchFrequency {


    public static void searcheachlocationfreq()
    {
        TreeMap<String, Integer> websiteFrequencyMap = new TreeMap<>();

        try {
            String filePath="F:\\SpringProject\\GITREPO\\flightpriceanalysis\\src\\search_frequency.txt";
            // Read searches from the file
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                String website = fileScanner.next();
                websiteFrequencyMap.put(website, websiteFrequencyMap.getOrDefault(website, 0) + 1);
            }

            // Display the frequency of each website
            System.out.println("Website Search Frequencies:");
            for (String website : websiteFrequencyMap.keySet()) {
                int frequency = websiteFrequencyMap.get(website);
                System.out.println(website + ": " + frequency + " searches");
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        catch(Exception e)
        {
            e.getMessage();
        }

    }
}
