import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
                "Java is a popular programming language.",
                "Data structures and algorithms are essential for Java developers.",
                "A good developer loves clean code, and clean code is easy to read."
        };

        Map<String, Integer> wordCounts = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            if (sentence == null || sentence.isBlank()) {
                continue;
            }
            String cleanedSentence = sentence.replaceAll("[,.]", " ").toLowerCase();
            String[] tokens = cleanedSentence.split("\\s+");

            for (String token : tokens) {
                if (!token.isBlank()) {
                    uniqueWords.add(token);
                    wordCounts.put(token, wordCounts.getOrDefault(token, 0) + 1);
                }
            }
        }

        List<String> repeatedWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() >= 2) {
                repeatedWords.add(entry.getKey() + " (" + entry.getValue() + " 次)");
            }
        }

        System.out.println("================ 文字索引統計系統 ================\n");
        System.out.println("總不重複單字數 (Set): " + uniqueWords.size());
        System.out.println("所有不重複單字: " + uniqueWords);
        System.out.println("\n完整單字統計 (Map):");
        wordCounts.forEach((k, v) -> System.out.printf("  %-12s : %d%n", k, v));

        System.out.println("\n出現至少兩次的單字 (出現頻率 >= 2):");
        if (repeatedWords.isEmpty()) {
            System.out.println("  (無符合條件的單字)");
        } else {
            repeatedWords.forEach(w -> System.out.println("  * " + w));
        }
    }
}