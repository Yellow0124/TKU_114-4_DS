import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> rawEnrollments = new ArrayList<>();
        rawEnrollments.add("Amy");
        rawEnrollments.add("");
        rawEnrollments.add(null);
        rawEnrollments.add("Ben");
        rawEnrollments.add("  ");
        rawEnrollments.add("Amy");
        rawEnrollments.add("Cara");
        rawEnrollments.add("Ben");
        rawEnrollments.add("David");
        rawEnrollments.add(null);
        rawEnrollments.add("Amy");

        System.out.println("================ 學生名單安全清理系統 ================\n");
        System.out.println("1. 清理前原始名單 (" + rawEnrollments.size() + " 筆):");
        System.out.println("   " + rawEnrollments);

        Iterator<String> iterator = rawEnrollments.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.isBlank()) {
                iterator.remove();
            }
        }

        for (int i = 0; i < rawEnrollments.size(); i++) {
            rawEnrollments.set(i, rawEnrollments.get(i).trim());
        }

        System.out.println("\n2. 使用 Iterator 清理無效資料後名單 (" + rawEnrollments.size() + " 筆):");
        System.out.println("   " + rawEnrollments);

        Set<String> seenNames = new HashSet<>();
        Set<String> duplicateNames = new HashSet<>();

        for (String name : rawEnrollments) {
            if (!seenNames.add(name)) {
                duplicateNames.add(name);
            }
        }

        System.out.println("\n3. 重複報名統計報告 (Set 分析):");
        System.out.println("   不重複學生人數: " + seenNames.size());
        System.out.println("   不重複名單: " + seenNames);
        System.out.println("   發現重複報名姓名: " + (duplicateNames.isEmpty() ? "無" : duplicateNames));
    }
}