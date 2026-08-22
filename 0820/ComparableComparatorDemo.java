import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class RankedStudent implements Comparable<RankedStudent> {
    private final String id;
    private final String name;
    private final int score;

    RankedStudent(String id, String name, int score) {
        this.id = (id == null || id.isBlank()) ? "S000" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.score = Math.max(0, Math.min(100, score));
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    @Override
    public int compareTo(RankedStudent other) {
        if (other == null)
            return 1;
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + " " + name + " " + score;
    }
}

public class ComparableComparatorDemo {
    public static void main(String[] args) {
        List<RankedStudent> students = new ArrayList<>();
        students.add(new RankedStudent("S103", "Cara", 75));
        students.add(new RankedStudent("S101", "Amy", 90));
        students.add(new RankedStudent("S102", "Alexander", 90));
        students.add(new RankedStudent("S104", "Ben", 85));

        students.sort(null);
        System.out.println("by id=" + students);

        Comparator<RankedStudent> byScore = Comparator
                .comparingInt(RankedStudent::getScore).reversed()
                .thenComparing(RankedStudent::getName);
        students.sort(byScore);
        System.out.println("by score=" + students);

        Comparator<RankedStudent> byNameLengthThenLexical = Comparator
                .comparingInt((RankedStudent s) -> s.getName().length())
                .thenComparing(RankedStudent::getName);
        students.sort(byNameLengthThenLexical);
        System.out.println("by name length=" + students);
    }
}