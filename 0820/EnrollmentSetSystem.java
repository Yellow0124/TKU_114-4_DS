import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final String studentName;

    Enrollment(String studentId, String courseCode, String studentName) {
        this.studentId = (studentId == null || studentId.isBlank()) ? "UNKNOWN_ID" : studentId.trim();
        this.courseCode = (courseCode == null || courseCode.isBlank()) ? "UNKNOWN_COURSE" : courseCode.trim();
        this.studentName = (studentName == null || studentName.isBlank()) ? "Unknown" : studentName.trim();
    }

    String getStudentId() {
        return studentId;
    }

    String getCourseCode() {
        return courseCode;
    }

    String getStudentName() {
        return studentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Enrollment that))
            return false;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return String.format("[%s - %s] %s", studentId, courseCode, studentName);
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        System.out.println("================ 課程報名身分集合系統 ================\n");

        Set<Enrollment> enrollmentSet = new HashSet<>();

        Enrollment e1 = new Enrollment("S101", "CS101", "Amy");
        Enrollment e2 = new Enrollment("S101", "DS201", "Amy");
        System.out.println("S101 報名 CS101: " + enrollmentSet.add(e1)); // true
        System.out.println("S101 報名 DS201: " + enrollmentSet.add(e2)); // true

        Enrollment e3 = new Enrollment("S102", "CS101", "Ben");
        System.out.println("S102 報名 CS101: " + enrollmentSet.add(e3)); // true

        Enrollment e1Duplicate = new Enrollment("S101", "CS101", "Amy Chen"); // 即使名字不同，身分相同
        System.out.println("S101 重複報名 CS101: " + enrollmentSet.add(e1Duplicate)); // false

        System.out.println("\n目前報名總數: " + enrollmentSet.size());
        System.out.println("目前名單: " + enrollmentSet);

        System.out.println("\n----------------- 身分比對與取消測試 -----------------");

        Enrollment queryKey = new Enrollment("S101", "DS201", "Amy Query");
        boolean exists = enrollmentSet.contains(queryKey);
        System.out.println("查詢 [S101 - DS201] 是否存在: " + exists); // true

        Enrollment cancelKey = new Enrollment("S101", "DS201", "Amy Cancel");
        boolean removed = enrollmentSet.remove(cancelKey);
        System.out.println("取消 [S101 - DS201] 報名結果: " + removed); // true

        boolean removedAgain = enrollmentSet.remove(cancelKey);
        System.out.println("再次取消 [S101 - DS201] 結果: " + removedAgain); // false

        System.out.println("\n最終有效報名名單 (" + enrollmentSet.size() + " 筆):");
        enrollmentSet.forEach(e -> System.out.println("  * " + e));
    }
}