package com.flightpriceanalysis.flightpriceanalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class SpellChecking {

    static Map<String, String> citiesCodes = new HashMap<String, String>();


    public static void initialLoadofWordcheck()
    {


        citiesCodes.put("windsor", "yqg");
        citiesCodes.put("toronto", "yto");
        citiesCodes.put("montreal", "yul");
        citiesCodes.put("vancouver", "yvr");
        citiesCodes.put("paris", "cdg");
        citiesCodes.put("new york", "jfk");
        citiesCodes.put("bengaluru", "blr");
        citiesCodes.put("bangalore", "blr");
        citiesCodes.put("hyderabad", "hyd");
    }

    public String wordCheckmain(String src) throws Exception
    {
        if(!src.matches("[a-zA-Z_]+")){
            System.out.println("Invalid source");
            System.out.println("Enter name of source");

        }
        src = src.toLowerCase();
        String fileword = src + "\n";
        String source = citiesCodes.get(src);
        if(source==null) {
            source=wordCheck(src);
            fileword = source + "\n";
            System.out.println(fileword);
            source = citiesCodes.get(source);

        }

        return fileword;
    }

    public Set<String> readFile()
    {
        String filePath = "src/destinations.txt"; // Replace with the actual path to your file
        Set<String> lineSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineSet.add(line.trim()); // Assuming you want to remove leading/trailing whitespaces
            }



        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return lineSet;
    }

    public  String wordCheck(String indexWord) throws Exception{

        //using array list to store the resulting list
        ArrayList<String> resultsArray = new ArrayList<String>();


        //converting to lower-case to have uniform results
        indexWord = indexWord.toLowerCase();
        int minDistance = Integer.MAX_VALUE;

        //Parsed Array stored in Set
        Set<String> wordsArray = readFile();

        for(String word : wordsArray) {
            int distance = minDistance(indexWord, word);
            if(distance <= minDistance) {
                minDistance = distance;
            }
            //condition for being similar words is having edit distance of 2
            if(distance <=2) {
                resultsArray.add(word);
            }

        }

        if(resultsArray.isEmpty())
        {
            throw new Exception("no relevant words were found..edit distance is more than 2");
        }

        //Printing list of similar words
        System.out.println("Did you mean " + indexWord + ":");
        System.out.println(resultsArray);
        String word = resultsArray.get(0);
        return word;
    }



    public static int minDistance(String firstWrd, String secondWrd) {

        //assigning length of both words to variables
        int x = firstWrd.length();
        int y = secondWrd.length();

        //assigning both lengths to an array
        int[][] editDist = new int[x + 1][y + 1];

        //Dynamic Programming algorithm
        for(int i = 0; i <= x; i++)
            editDist[i][0] = i;
        for(int i = 1; i <= y; i++)
            editDist[0][i] = i;

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(firstWrd.charAt(i) == secondWrd.charAt(j))
                    editDist[i + 1][j + 1] = editDist[i][j];
                else {
                    int a = editDist[i][j];
                    int b = editDist[i][j + 1];
                    int c = editDist[i + 1][j];
                    editDist[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    editDist[i + 1][j + 1]++;
                }
            }
        }
        return editDist[x][y];


    }



}
