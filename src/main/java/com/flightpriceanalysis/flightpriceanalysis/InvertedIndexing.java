package com.flightpriceanalysis.flightpriceanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndexing {

    static TrieST<Map<String, Integer>> ht_wordTrie = new TrieST<>();

    // Constructing_the_trie_for_given_filename_and_list
    static void constructTrie(String filename, List<String> words) {
        // HashMap_to_store_word_frequencies
        HashMap<String, Integer> ht_wordFreq = new HashMap<String, Integer>();
        for (String word : words) {
            if (!ht_wordFreq.containsKey(word)) {
                ht_wordFreq.put(word, 1);
            } else {
                Integer x = ht_wordFreq.get(word);
                ht_wordFreq.replace(word, x + 1);
            }
        }

        // Building_the_inverted_index_for_words
        for (String key : ht_wordFreq.keySet()) {
            if (!ht_wordTrie.contains(key)) {
                // HashMap_to_associate_filename_and_frequency
                HashMap<String, Integer> ht_rowMap = new HashMap<String, Integer>();
                ht_rowMap.put(filename, ht_wordFreq.get(key));
                ht_wordTrie.put(key, ht_rowMap);
            }
        }

        // Printing_words_in_trie_with_associated_maps
        for (String key : ht_wordTrie.keys()) {
            System.out.println(key + " " + ht_wordTrie.get(key));
        }

    }
}