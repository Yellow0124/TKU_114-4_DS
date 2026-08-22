import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class CourseEnrollmentKey {
    private final String studentId;
    private final String courseCode;
    private final String studentName;

    CourseEnrollmentKey(String studentId, String courseCode, String studentName) {
        this.studentId = (studentId == null || studentId.isBlank()) ? "S000" : studentId.trim();
        this.courseCode = (courseCode == null || courseCode.isBlank()) ? "C000" : courseCode.trim();
        this.studentName = (studentName == null || studentName.isBlank()) ? "Unknown" : studentName.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseEnrollmentKey key)) {
            return false;
        }
        return Objects.equals(studentId, key.studentId)
                && Objects.equals(courseCode, key.courseCode);
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

public class HashSetEqualityDemo {
    public static void main(String[] args) {
        Set<CourseEnrollmentKey> enrollments = new HashSet<>();

        System.out.println(enrollments.add(
                new CourseEnrollmentKey("S101", "CS101", "Amy")));

        System.out.println(enrollments.add(
                new CourseEnrollmentKey("S101", "CS101", "Amy Chen")));

        System.out.println(enrollments.add(
                new CourseEnrollmentKey("S101", "DS201", "Amy")));

        System.out.println(enrollments.add(
                new CourseEnrollmentKey("S102", "CS101", "Ben")));

        System.out.println("size=" + enrollments.size());
        System.out.println("選課名單=" + enrollments);
    }
}