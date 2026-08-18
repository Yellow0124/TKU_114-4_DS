class Customer {
    private final String customerId;
    private final String name;
    private final String email;

    public Customer(String customerId, String name, String email) {
        this.customerId = (customerId == null || customerId.isBlank()) ? "UNKNOWN" : customerId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.email = (email == null || email.isBlank()) ? "unspecified@example.com" : email.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)", name, customerId, email);
    }
}

class OrderItem {
    private final String itemName;
    private final int unitPrice;
    private final int quantity;

    public OrderItem(String itemName, int unitPrice, int quantity) {
        this.itemName = (itemName == null || itemName.isBlank()) ? "Unnamed Item" : itemName.trim();
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(1, quantity);
    }

    public int getSubtotal() {
        return unitPrice * quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("%-18s | 單價: NT$%5d | 數量: %2d | 小計: NT$%6d",
                itemName, unitPrice, quantity, getSubtotal());
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;

    public CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = (orderId == null || orderId.isBlank()) ? "UNKNOWN" : orderId.trim();
        this.customer = (customer == null) ? new Customer("C-UNKNOWN", "Unknown Customer", "") : customer;
        this.items = new OrderItem[Math.max(1, capacity)];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (item == null) {
            System.out.println("加入失敗：品項物件不可為 null");
            return false;
        }
        if (itemCount >= items.length) {
            System.out.printf("加入失敗：訂單 %s 容量已滿 (上限 %d 項)%n", orderId, items.length);
            return false;
        }
        items[itemCount] = item;
        itemCount++;
        return true;
    }

    public int calculateTotalAmount() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public int calculateTotalQuantity() {
        int totalQty = 0;
        for (int i = 0; i < itemCount; i++) {
            totalQty += items[i].getQuantity();
        }
        return totalQty;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void printSummary() {
        System.out.println("================================================================================");
        System.out.printf("訂單編號: %-10s | 顧客資訊: %s%n", orderId, customer);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("品項明細：");
        for (int i = 0; i < itemCount; i++) {
            System.out.printf("  %d. %s%n", (i + 1), items[i]);
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("總品項數: %d 種 | 商品總件數: %d 件 | 訂單總金額: NT$ %,d%n",
                itemCount, calculateTotalQuantity(), calculateTotalAmount());
        System.out.println("================================================================================\n");
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customerAmy = new Customer("C101", "Amy", "amy@example.com");
        Customer customerBen = new Customer("C102", "Ben", "ben@example.com");

        CustomerOrder order1 = new CustomerOrder("ORD-2026-001", customerAmy, 3);
        order1.addItem(new OrderItem("機械鍵盤", 2800, 1));
        order1.addItem(new OrderItem("人體工學滑鼠", 1200, 2));
        order1.addItem(new OrderItem("USB-C 傳輸線", 250, 3));

        System.out.println(">>> 測試訂單容量上限防護：");
        order1.addItem(new OrderItem("超大型滑鼠墊", 450, 1));
        System.out.println();

        order1.printSummary();

        CustomerOrder order2 = new CustomerOrder("ORD-2026-002", customerBen, 2);
        order2.addItem(new OrderItem("27 吋電競螢幕", 8500, 1));
        order2.addItem(new OrderItem("HDMI 2.1 高畫質線", 490, 2));

        order2.printSummary();
    }
}