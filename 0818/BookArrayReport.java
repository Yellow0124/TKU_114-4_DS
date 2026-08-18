class Book {
    private String id;
    private String title;
    private int price;
    private int stock;

    public Book(String id, String title, int price, int stock) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.title = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    public int getTotalValue() {
        return price * stock;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-18s | 單價: NT$%4d | 庫存: %2d 本 | 庫存總值: NT$%6d",
                id, title, price, stock, getTotalValue());
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
                new Book("B101", "Java 核心技術卷一", 780, 5),
                new Book("B102", "資料結構圖解入門", 520, 2), // 低庫存
                new Book("B103", "演算法設計與分析", 890, 8), // 最高單價
                new Book("B104", "Clean Code 簡潔之道", 650, 3), // 低庫存
                new Book("B105", "系統架構設計模式", 820, 1) // 低庫存
        };

        System.out.println(">>> 1. 所有書籍清單：");
        for (Book book : books) {
            System.out.println("  " + book);
        }
        System.out.println("----------------------------------------------------------------------------\n");

        long totalInventoryValue = 0;
        for (Book book : books) {
            totalInventoryValue += book.getTotalValue();
        }
        System.out.printf(">>> 2. 全店庫存總價值：NT$ %,d%n", totalInventoryValue);
        System.out.println("----------------------------------------------------------------------------\n");

        Book highestPriceBook = books[0];
        for (int i = 1; i < books.length; i++) {
            if (books[i].getPrice() > highestPriceBook.getPrice()) {
                highestPriceBook = books[i];
            }
        }
        System.out.println(">>> 3. 單價最高的書籍：");
        System.out.printf("  書名：%s (編號: %s) | 單價：NT$ %d%n",
                highestPriceBook.getTitle(), highestPriceBook.getId(), highestPriceBook.getPrice());
        System.out.println("----------------------------------------------------------------------------\n");

        System.out.println(">>> 4. 庫存偏低警示清單 (庫存 <= 3 本)：");
        int lowStockCount = 0;
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.printf("   需補貨 -> [%s] %-18s (目前庫存: %d 本)%n",
                        book.getId(), book.getTitle(), book.getStock());
                lowStockCount++;
            }
        }
        System.out.printf("  統計：共有 %d 本書籍需要安排進貨補貨。%n", lowStockCount);
        System.out.println("\n============================================================================");
    }
}
