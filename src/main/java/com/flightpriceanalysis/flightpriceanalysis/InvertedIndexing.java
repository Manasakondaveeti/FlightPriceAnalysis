package com.flightpriceanalysis.flightpriceanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndexing {

    static TrieST<Map<String, Integer>> wordTrie = new TrieST<>();

    static void constructTrie(String filename, List<String> words) {
        HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
        for(String word: words) {
            if(!wordFreq.containsKey(word)) {
                wordFreq.put(word,  1);
            } else {
                Integer x = wordFreq.get(word);
                wordFreq.replace(word, x+1);
            }
        }

        for(String key: wordFreq.keySet()) {
            if(!wordTrie.contains(key)) {
                HashMap<String, Integer> rowMap = new HashMap<String, Integer>();
                rowMap.put(filename, wordFreq.get(key));
                wordTrie.put(key, rowMap);
            }
        }

        for (String key : wordTrie.keys()) {
            System.out.println(key + " " + wordTrie.get(key));
        }

    }
}
