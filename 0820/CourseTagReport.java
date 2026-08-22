import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] rawTags = { "Java", "OOP", "DataStructure", "Java", "Algorithm", "OOP", "Java" };

        List<String> tagList = new ArrayList<>();
        Set<String> tagSet = new HashSet<>();
        Map<String, Integer> tagCounts = new HashMap<>();

        for (String tag : rawTags) {
            tagList.add(tag);
            tagSet.add(tag);
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        }

        System.out.println("原始順序 (List): " + tagList);
        System.out.println("不重複標籤 (Set): " + tagSet);
        System.out.println("次數統計 (Map): " + tagCounts);
    }
}