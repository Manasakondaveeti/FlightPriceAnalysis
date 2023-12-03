package com.flightpriceanalysis.flightpriceanalysis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {


    //    private final Trie[] children;
//    private boolean isEndOfWord;
//
//    public Trie() {
//        this.children = new Trie[26]; // Assuming only lowercase English letters
//        this.isEndOfWord = false;
//    }
//
//    public void insert(String word) {
//        Trie node = this;
//        for (char c : word.toCharArray()) {
//            int index = c - 'a';
//            if (node.children[index] == null) {
//                node.children[index] = new Trie();
//            }
//            node = node.children[index];
//        }
//        node.isEndOfWord = true;
//    }
    private final Map<Character, Trie> children;
    private boolean isEndOfWord;

    public Trie() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }

    public void insert(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new Trie());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    public List<String> findWordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (c == ' ') {
                // If it's a space, consider it as a separate character
                c = 'z' + 1; // Choose an index beyond 'z'
            }
            if (node.children.containsKey(c)) {
                node = node.children.get(c);
            } else {
                return words; // No words with the given prefix
            }
        }
        findWords(node, prefix, words);
        return words;
    }

    private void findWords(Trie node, String currentWord, List<String> words) {
        if (node.isEndOfWord) {
            words.add(currentWord);
        }
        for (char c : node.children.keySet()) {
            if (c == 'z' + 1) {
                // If it's the special character for space
                findWords(node.children.get(c), currentWord + ' ', words);
            } else {
                findWords(node.children.get(c), currentWord + c, words);
            }
        }
    }


    public void printAllWords() {
        List<String> words = findWordsWithPrefix("");
        System.out.println("All inserted words in the trie:");
        for (String word : words) {
            System.out.println(word);
        }
    }
}

