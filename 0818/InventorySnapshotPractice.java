import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    public InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = (warehouseId == null || warehouseId.isBlank()) ? "UNKNOWN" : warehouseId.trim();
        this.quantities = (quantities == null) ? new int[0] : Arrays.copyOf(quantities, quantities.length);
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    public int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("InventorySnapshot{warehouseId='%s', quantities=%s}",
                warehouseId, Arrays.toString(quantities));
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] sourceData = { 5, 0, 3, 0 };
        InventorySnapshot snapshot = new InventorySnapshot("WH-NORTH", sourceData);

        System.out.println(">>> 1. 初始快照內容：");
        System.out.println("快照狀態：" + snapshot);
        System.out.println("總庫存量 totalQuantity()   : " + snapshot.totalQuantity() + " (預期: 8)");
        System.out.println("缺貨品項 outOfStockCount() : " + snapshot.outOfStockCount() + " (預期: 2)");
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 2. 驗證 Defensive Copy - 修改來源陣列 sourceData：");
        System.out.println("執行: sourceData[0] = 999; sourceData[1] = 888;");
        sourceData[0] = 999;
        sourceData[1] = 888;
        System.out.println("修改後的外部 sourceData：" + Arrays.toString(sourceData));
        System.out.println("快照內部 quantities     ：" + Arrays.toString(snapshot.getQuantities()) + " (不受外部影響)");
        System.out.println("總庫存量依然為          ：" + snapshot.totalQuantity());
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 3. 驗證 Defensive Copy - 修改 getter 回傳之陣列：");
        int[] receivedData = snapshot.getQuantities();
        System.out.println("執行: receivedData[2] = 0;");
        receivedData[2] = 0;
        System.out.println("修改後的 receivedData   ：" + Arrays.toString(receivedData));
        System.out.println("快照內部 quantities     ：" + Arrays.toString(snapshot.getQuantities()) + " (內部數據未被篡改)");
        System.out.println("--------------------------------------------------------------------------------\n");

        System.out.println(">>> 4. 邊界測試 - Constructor 傳入 null 陣列：");
        InventorySnapshot nullSnapshot = new InventorySnapshot("WH-EMPTY", null);
        System.out.println("null 快照狀態：" + nullSnapshot);
        System.out.println("陣列長度     : " + nullSnapshot.getQuantities().length);
        System.out.println("總庫存量     : " + nullSnapshot.totalQuantity());
        System.out.println("缺貨品項數   : " + nullSnapshot.outOfStockCount());

        System.out.println("\n================================================================================");
    }
}
