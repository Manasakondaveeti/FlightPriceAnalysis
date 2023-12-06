package com.flightpriceanalysis.flightpriceanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/* The above imports are done to for reading files, handling operations and
 Working with lists.*/
public class WordCompletion {

    private static Trie destinationsTrie;
    // a static variable destinationsTrie of type Trie is declared, which will be used to store and manage words for word completion.

    // creating a wordcompletion function which is static and takes a user type word as an input and will return with the suggestions.
    public static  List<String> wordCompletion(String indexWord) {

        //here if a trie is already created then it will not create it again.
        if (destinationsTrie == null) {
            constructTrieDestinations();
            //this is a function which create initial trie with name of destinations.
        }
        System.out.println(indexWord); //Here we are printing the word that is entered by user.
        List<String> suggestions=destinationsTrie.findWordsWithPrefix(indexWord);
        //here we are calling findWordsWithPrefix function which looks for words with given prefix

        System.out.println("Suggestionsfor'"+indexWord+"':" + suggestions);
        for(String suggestion:suggestions){
            System.out.println(suggestion); //this codeblock prints the suggestions on commandline ,
        }


        return suggestions; //now we return the suggestions
    }

    // a function which create initial trie with name of destinations.
    private static void constructTrieDestinations(){

        destinationsTrie = new Trie();

        String filePath = "src\\destinations.txt";
        //this is the path to the file which contains words(destinations).

        //buffered reader is a library used to read the files
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // Insert each word into the trie
                System.out.println(word);
                destinationsTrie.insert(word);
                //this insert function inserts the word that is splitted in a trie.
            }

        } catch (IOException e) {
            e.printStackTrace(); //if there is any exception then it will come under this block.
        }
        destinationsTrie.printAllWords();
    }

}
