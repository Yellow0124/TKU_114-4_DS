class CourseGrade {
    private final String studentId;
    private final String name;
    private final int regularScore;
    private final int midtermScore;
    private final int finalExamScore;
    private final int attendanceScore;

    public CourseGrade(String studentId, String name, int regularScore, int midtermScore, int finalExamScore,
            int attendanceScore) {
        this.studentId = (studentId == null || studentId.isBlank()) ? "UNKNOWN" : studentId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.regularScore = clampScore(regularScore);
        this.midtermScore = clampScore(midtermScore);
        this.finalExamScore = clampScore(finalExamScore);
        this.attendanceScore = clampScore(attendanceScore);
    }

    private int clampScore(int score) {
        return Math.max(0, Math.min(100, score));
    }

    public double calculateFinalScore() {
        return (regularScore * 0.50) + (midtermScore * 0.20) + (finalExamScore * 0.20) + (attendanceScore * 0.10);
    }

    public String getLevel() {
        double finalScore = calculateFinalScore();
        if (finalScore >= 90.0) {
            return "A";
        } else if (finalScore >= 80.0) {
            return "B";
        } else if (finalScore >= 70.0) {
            return "C";
        } else if (finalScore >= 60.0) {
            return "D";
        } else {
            return "F";
        }
    }

    public boolean isPassed() {
        return calculateFinalScore() >= 60.0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getRegularScore() {
        return regularScore;
    }

    public int getMidtermScore() {
        return midtermScore;
    }

    public int getFinalExamScore() {
        return finalExamScore;
    }

    public int getAttendanceScore() {
        return attendanceScore;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %-8s | 平時(50%%): %3d | 期中(20%%): %3d | 期末(20%%): %3d | 出席(10%%): %3d | 總分: %5.1f | 等第: %s",
                studentId, name, regularScore, midtermScore, finalExamScore, attendanceScore, calculateFinalScore(),
                getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = {
                new CourseGrade("S001", "Amy", 95, 88, 92, 100),
                new CourseGrade("S002", "Ben", 60, 55, 58, 80),
                new CourseGrade("S003", "Cara", 45, 50, 40, 60), // 不及格
                new CourseGrade("S004", "David", 85, 90, 80, 90),
                new CourseGrade("S005", "Emma", 120, 30, 20, -10) // 邊界值測試 (120->100, -10->0)
        };

        System.out.println(">>> 1. 全班學生成績清單：");
        for (CourseGrade grade : grades) {
            System.out.println("  " + grade);
        }
        System.out
                .println("-----------------------------------------------------------------------------------------\n");

        double totalSum = 0.0;
        for (CourseGrade grade : grades) {
            totalSum += grade.calculateFinalScore();
        }
        double averageScore = totalSum / grades.length;
        System.out.printf(">>> 2. 全班平均總成績：%.2f 分%n", averageScore);
        System.out
                .println("-----------------------------------------------------------------------------------------\n");

        CourseGrade topStudent = grades[0];
        for (int i = 1; i < grades.length; i++) {
            if (grades[i].calculateFinalScore() > topStudent.calculateFinalScore()) {
                topStudent = grades[i];
            }
        }
        System.out.printf(">>> 3. 全班最高分學生：%s (%s) | 總分：%.1f 分 (等第: %s)%n",
                topStudent.getName(), topStudent.getStudentId(), topStudent.calculateFinalScore(),
                topStudent.getLevel());
        System.out
                .println("-----------------------------------------------------------------------------------------\n");

        System.out.println(">>> 4. 學期成績不及格名單 (總分 < 60.0)：");
        int failCount = 0;
        for (CourseGrade grade : grades) {
            if (!grade.isPassed()) {
                System.out.printf("不及格 -> [%s] %-8s | 總分: %5.1f 分 | 等第: %s%n",
                        grade.getStudentId(), grade.getName(), grade.calculateFinalScore(), grade.getLevel());
                failCount++;
            }
        }
        System.out.printf("  統計：共有 %d 位同學學期成績未達及格標準。%n", failCount);

        System.out
                .println("\n=========================================================================================");
    }
}
