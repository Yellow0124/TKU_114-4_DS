class Equipment {
    private String id;
    private String name;
    private int availableCount;

    public Equipment(String id, String name, int availableCount) {
        this.id = (id == null || id.isBlank()) ? "Unknown" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.availableCount = Math.max(0, availableCount);
    }

    public boolean borrowOne() {
        if (availableCount <= 0) {
            return false;
        }
        availableCount--;
        return true;
    }

    public void returnItems(int quantity) {
        if (quantity > 0) {
            availableCount += quantity;
        }
    }

    public String grtId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    @Override
    public String toString() {
        return String.format("[%s] 設備名稱: %-10s | 可借數量: %2d", id, name, availableCount);
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment eq1 = new Equipment("EQ-101", "Projector", 2);
        Equipment eq2 = new Equipment("   ", "", -5);

        System.out.println(">>> 初始設備狀態：");
        System.out.println(eq1);
        System.out.println(eq2);
        System.out.println("------------------------------------------------------------\n");

        System.out.println(">>> 測試 eq1 (Projector) 借用流程：");
        System.out.println("第一次借用 (庫存 2 -> 1): " + (eq1.borrowOne() ? "成功" : "失敗"));
        System.out.println("第二次借用 (庫存 1 -> 0): " + (eq1.borrowOne() ? "成功" : "失敗"));
        System.out.println("第三次借用 (庫存 0，預期失敗): " + (eq1.borrowOne() ? "成功" : "失敗"));
        System.out.println("當前狀態: " + eq1);
        System.out.println("------------------------------------------------------------\n");

        System.out.println(">>> 測試 eq1 歸還流程：");
        System.out.println("嘗試歸還 -3 件 (無效數量，庫存應不變)...");
        eq1.returnItems(-3);
        System.out.println("當前狀態: " + eq1);

        System.out.println("正常歸還 5 件...");
        eq1.returnItems(5);
        System.out.println("當前狀態: " + eq1);
        System.out.println("------------------------------------------------------------\n");

        System.out.println(">>> 測試 eq2 (初始庫存為 0)：");
        System.out.println("借用嘗試: " + (eq2.borrowOne() ? "成功" : "失敗"));
        System.out.println("補貨/歸還 3 件...");
        eq2.returnItems(3);
        System.out.println("當前狀態: " + eq2);
        System.out.println("借用 1 件: " + (eq2.borrowOne() ? "成功" : "失敗"));
        System.out.println("最終狀態: " + eq2);
    }
}
