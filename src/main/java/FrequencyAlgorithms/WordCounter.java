package FrequencyAlgorithms;

import java.util.*;

public class WordCounter {

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
    private static boolean ASC = true;
    private static boolean DESC = false;
    private static int MAX = 10;

    public WordCounter(String input) {
        String[] words = input.split("\\s+");

        for (String word : words) {
            word = word.trim().toLowerCase();

            try {
                this.wordMap.put(word, this.wordMap.get(word) + 1);
            } catch (NullPointerException e) {
                this.wordMap.put(word, 1);
            }
        }

        this.wordMap = sortByComparator(this.wordMap, DESC);
    }

    private static HashMap<String, Integer> sortByComparator(HashMap<String, Integer> unsortMap, final boolean order) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (order)
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        int i = 0;
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
            if (i++ > MAX)
                break;
        }

        return sortedMap;
    }

    @Override
    public String toString() {
        return this.wordMap.toString();
    }
    /*
    private static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }
    */
}