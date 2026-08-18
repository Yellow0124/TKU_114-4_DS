import java.util.Objects;

class LibraryMember {
    private final String memberId;
    private String name;
    private String email;

    public LibraryMember(String memberId, String name, String email) {
        this.memberId = (memberId == null || memberId.isBlank()) ? "UNKNOWN" : memberId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.email = (email == null || email.isBlank()) ? "unspecified@example.com" : email.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("LibraryMember{memberId='%s', name='%s', email='%s'}", memberId, name, email);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LibraryMember member)) {
            return false;
        }
        return Objects.equals(this.memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember memberA = new LibraryMember("M1001", "Amy", "amy@example.com");
        LibraryMember memberB = new LibraryMember("M1001", "Amy Chen", "amy.chen@newdomain.com");

        LibraryMember memberC = new LibraryMember("M1002", "Bob", "bob@example.com");

        LibraryMember aliasOfA = memberA;

        System.out.println(">>> 物件詳細資訊：");
        System.out.println("A: " + memberA);
        System.out.println("B: " + memberB);
        System.out.println("C: " + memberC);
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 測試 1：比較 memberA 與 memberB (相同 memberId，不同 email)");
        System.out.println("  memberA == memberB      (記憶體位址比較): " + (memberA == memberB));
        System.out.println("  memberA.equals(memberB) (業務身分 ID 比較): " + memberA.equals(memberB));
        System.out.println("  memberA 與 memberB 的 hashCode 是否相同  : " + (memberA.hashCode() == memberB.hashCode()));
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 測試 2：比較 memberA 與 aliasOfA (Alias 參照同一物件)");
        System.out.println("  memberA == aliasOfA     : " + (memberA == aliasOfA));
        System.out.println("  memberA.equals(aliasOfA): " + memberA.equals(aliasOfA));
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 測試 3：比較 memberA 與 memberC (不同 memberId)");
        System.out.println("  memberA == memberC      : " + (memberA == memberC));
        System.out.println("  memberA.equals(memberC) : " + memberA.equals(memberC));
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 測試 4：邊界條件防護測試 (比對 null 與其他型別)");
        System.out.println("  memberA.equals(null)       : " + memberA.equals(null) + " (不可拋出 NullPointerException)");
        System.out.println("  memberA.equals(\"M1001\")     : " + memberA.equals("M1001") + " (傳入 String 型別應為 false)");

        System.out.println("\n================================================================================");
    }
}
