package com.flightpriceanalysis.flightpriceanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WordCompletion {


    public static  List<String> wordCompletion(String indexWord) {

        //using array list to store the resulting list
        constructTrieDestinations();
        System.out.println(indexWord);
        List<String> suggestions=destinationsTrie.findWordsWithPrefix(indexWord);


        System.out.println("Suggestionsfor'"+indexWord+"':" + suggestions);
        for(String suggestion:suggestions){
            System.out.println(suggestion);
        }


        return suggestions;
    }

    static Trie destinationsTrie = new Trie();
    static void constructTrieDestinations(){

        // Path to your text file
        String filePath = "src\\destinations.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // Insert each word into the trie
                System.out.println(word);
                destinationsTrie.insert(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        destinationsTrie.printAllWords();
    }

}
