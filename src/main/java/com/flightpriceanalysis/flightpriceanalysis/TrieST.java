package com.flightpriceanalysis.flightpriceanalysis;

public class TrieST<Value> {
    private static final int HT_R = 256;        // extended_ASCII

    private Node HT_root;      // root_of_trie
    private int HT_N;          // number_of_keys_in_trie

    // R-way_trie_node
    private static class Node {
        private Object val;
        private Node[] next = new Node[HT_R];
    }

    public TrieST() {
    }

    // Get_value_for_the_key
    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node x = get(HT_root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    // Check_if_key_exists
    public boolean contains(String key) {
        return get(key) != null;
    }

    /* Get_node_for_the_key */
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    // Insert_key-value_pair
    public void put(String key, Value val) {
        if (val == null) delete(key);
        else HT_root = put(HT_root, key, val, 0);
    }

    // Recursive_put_operation
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) HT_N++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    // Get_size_of_the_trie
    public int size() {
        return HT_N;
    }

    // Check_if_trie_is_empty
    public boolean isEmpty() {
        return size() == 0;
    }

    /* Get_all_keys_in_trie */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /* Get_keys_with_a_certain_prefix */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(HT_root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    // Collect_keys_with_given_prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char c = 0; c < HT_R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /* Get_keys_that_match_a_pattern */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(HT_root, new StringBuilder(), pattern, results);
        return results;
    }

    // Collect_keys_that_match_a_given_pattern
    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < HT_R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    // Get_the_longest_prefix_of_a_given_query
    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(HT_root, query, 0, 0);
        return query.substring(0, length);
    }

    // Recursive_function_for_longest_prefix
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    // Delete_a_key_from_the_trie
    public void delete(String key) {
        HT_root = delete(HT_root, key, 0);
    }

    // Recursive_function_to_delete_a_key
    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) HT_N--;
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if (x.val != null) return x;
        for (int c = 0; c < HT_R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

    /**
     * Unit_tests_the_<tt>TrieSET</tt>_data_type.
     */
}