import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class StoreProduct implements Comparable<StoreProduct> {
    private final String id;
    private final String name;
    private final int price;
    private final int stock;

    StoreProduct(String id, String name, int price, int stock) {
        this.id = (id == null || id.isBlank()) ? "P000" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    // Natural order: 依 id 升冪排序
    @Override
    public int compareTo(StoreProduct other) {
        if (other == null)
            return 1;
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %-12s | 價格: NT$%5d | 庫存: %3d", id, name, price, stock);
    }
}

public class ProductComparatorPractice {
    public static void main(String[] args) {
        List<StoreProduct> originalProducts = List.of(
                new StoreProduct("P104", "Keyboard", 1500, 20),
                new StoreProduct("P101", "Mouse", 800, 50),
                new StoreProduct("P103", "Monitor", 5500, 15),
                new StoreProduct("P105", "Headset", 1500, 35), // 與 Keyboard 同價
                new StoreProduct("P102", "Webcam", 1200, 20) // 與 Keyboard 同庫存
        );

        System.out.println("=== 原始順序 ===");
        originalProducts.forEach(System.out::println);

        List<StoreProduct> byIdList = new ArrayList<>(originalProducts);
        byIdList.sort(null);
        System.out.println("\n=== 1. Natural Order (id 升冪) ===");
        byIdList.forEach(System.out::println);

        List<StoreProduct> byPriceList = new ArrayList<>(originalProducts);
        Comparator<StoreProduct> byPriceThenName = Comparator
                .comparingInt(StoreProduct::getPrice)
                .thenComparing(StoreProduct::getName);
        byPriceList.sort(byPriceThenName);
        System.out.println("\n=== 2. 依 price 升冪 (同價依 name) ===");
        byPriceList.forEach(System.out::println);

        List<StoreProduct> byStockList = new ArrayList<>(originalProducts);
        Comparator<StoreProduct> byStockThenId = Comparator
                .comparingInt(StoreProduct::getStock).reversed()
                .thenComparing(StoreProduct::getId);
        byStockList.sort(byStockThenId);
        System.out.println("\n=== 3. 依 stock 降冪 (同庫存依 id) ===");
        byStockList.forEach(System.out::println);
    }
}