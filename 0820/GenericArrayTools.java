import java.util.Arrays;
import java.util.Objects;

public class GenericArrayTools {

    public static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) {
            return 0;
        }
        int count = 0;
        for (T item : data) {
            if (Objects.equals(item, target)) {
                count++;
            }
        }
        return count;
    }

    public static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    public static <T> void swap(T[] data, int first, int second) {
        if (data == null || data.length <= 1) {
            return;
        }
        if (first < 0 || first >= data.length || second < 0 || second >= data.length) {
            return;
        }
        if (first == second) {
            return;
        }
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] words = { "Java", "Python", "Java", "C++", null, "Java" };
        System.out.println("Java 出現次數: " + countMatches(words, "Java"));
        System.out.println("null 出現次數: " + countMatches(words, null));
        System.out.println("空陣列比對次數: " + countMatches(new String[0], "Java"));
        System.out.println("null 陣列比對次數: " + countMatches(null, "Java"));

        System.out.println();

        Integer[] numbers = { 10, 20, 30, 40, 50 };
        System.out.println("最後一個數字: " + last(numbers));
        System.out.println("空陣列 last: " + last(new Integer[0]));
        System.out.println("null 陣列 last: " + last(null));

        System.out.println();

        String[] languages = { "Java", "Python", "Go", "Rust" };
        System.out.println("交換前: " + Arrays.toString(languages));

        swap(languages, 1, 3);
        System.out.println("交換 (1, 3) 後: " + Arrays.toString(languages));

        swap(languages, -1, 2);
        System.out.println("不合法 index 交換後 (維持不變): " + Arrays.toString(languages));
    }
}