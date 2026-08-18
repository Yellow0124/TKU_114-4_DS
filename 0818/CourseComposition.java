class Instructor {
    private String id;
    private String name;

    public Instructor(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", id, name);
    }
}

class Course {
    private String courseCode;
    private String title;
    private Instructor instructor;

    public Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = (courseCode == null || courseCode.isBlank()) ? "UNKNOWN" : courseCode.trim();
        this.title = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        this.instructor = (instructor == null) ? new Instructor("UNKNOWN", "Unassigned") : instructor;
    }

    public String summary() {
        return String.format("課程代碼: %-6s | 課名: %-16s | 授課教師: %s",
                courseCode, title, instructor);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public Instructor getInstructor() {
        return instructor;
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor profWang = new Instructor("INS-001", "王大明");
        Instructor profLin = new Instructor("INS-002", "林小華");

        Course course1 = new Course("CS101", "計算機概論", profWang);
        Course course2 = new Course("CS201", "資料結構與演算法", profWang);
        Course course3 = new Course("IM301", "資料庫管理系統", profLin);

        System.out.println(">>> 初始課程資訊清單：");
        System.out.println(course1.summary());
        System.out.println(course2.summary());
        System.out.println(course3.summary());
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 驗證 Reference 特性：更新 profWang 的職稱與姓名...");
        profWang.setName("王大明 教授 (Prof. Wang)");

        System.out.println("\n更新後的課程資訊：");
        System.out.println(course1.summary());
        System.out.println(course2.summary());
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 物件參考驗證：");
        System.out.println("course1 與 course2 的授課教師是否指向同一物件實體 (==): "
                + (course1.getInstructor() == course2.getInstructor()));
    }
}
