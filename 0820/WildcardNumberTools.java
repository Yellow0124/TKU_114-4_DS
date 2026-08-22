import java.util.ArrayList;
import java.util.List;

public class WildcardNumberTools {
    public static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Number val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.size();
    }

    public static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = -Double.MAX_VALUE;
        boolean hasValidElement = false;
        for (Number val : values) {
            if (val != null) {
                max = Math.max(max, val.doubleValue());
                hasValidElement = true;
            }
        }
        return hasValidElement ? max : Double.NaN;
    }

    public static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>(List.of(10, 25, 40));
        System.out.printf("Integer average: %.2f%n", average(intList));
        System.out.printf("Integer maximum: %.2f%n", maximum(intList));

        List<Double> doubleList = List.of(3.5, 9.2, 1.8, 6.4);
        System.out.printf("Double average: %.2f%n", average(doubleList));
        System.out.printf("Double maximum: %.2f%n", maximum(doubleList));

        List<Integer> emptyList = new ArrayList<>();
        System.out.printf("Empty average: %.2f%n", average(emptyList));
        System.out.println("Empty maximum: " + maximum(emptyList));

        List<Number> numberList = new ArrayList<>();
        addRange(numberList, 1, 4);
        System.out.println("addRange(1, 4): " + numberList);

        addRange(numberList, 5, 2);
        System.out.println("addRange(5, 2) [無變更]: " + numberList);
    }
}