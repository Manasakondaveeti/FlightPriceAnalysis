package com.flightpriceanalysis.flightpriceanalysis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private final Map<Character, Trie> children;//it associates characters with corresponding child Trie nodes.
    //each character in a word is associated with a Trie node representing the continuation of the word
    private boolean isEndOfWord; //This is declared as a flag to check if word has ended or not.

    public Trie() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }

    // insert function is used to insert a word in a trie
    public void insert(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new Trie());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    //this function return lis of words with given prefix
    public List<String> findWordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (c == ' ') {
                // If it's a space, it will consider it as a seperate character
                c = 'z' + 1;
            }
            if (node.children.containsKey(c)) {
                node = node.children.get(c);
            } else {
                return words; // No words with the given prefix
            }
        }
        findWords(node, prefix, words);//it recursively look into all words starting with the prefix
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

    //Print function below is used to print all the words in a trie..
    public void printAllWords() {
        List<String> words = findWordsWithPrefix("");
        System.out.println("All inserted words in the trie:");
        for (String word : words) {
            System.out.println(word);
        }
    }
}

