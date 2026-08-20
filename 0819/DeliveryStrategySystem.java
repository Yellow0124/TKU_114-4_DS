interface DeliveryMethod {
    int calculateShippingFee(int orderAmount, double weightKg);

    String getEstimatedDays();

    String getName();
}

class HomeDelivery implements DeliveryMethod {
    private final int baseFee = 100;

    @Override
    public int calculateShippingFee(int orderAmount, double weightKg) {
        if (orderAmount >= 1500) {
            return 0;
        }
        int extraFee = weightKg > 5.0 ? (int) Math.ceil(weightKg - 5.0) * 20 : 0;
        return baseFee + extraFee;
    }

    @Override
    public String getEstimatedDays() {
        return "1-2 工作天";
    }

    @Override
    public String getName() {
        return "宅配到府";
    }
}

class ConvenienceStoreDelivery implements DeliveryMethod {
    private final int storeFee = 60;

    @Override
    public int calculateShippingFee(int orderAmount, double weightKg) {
        if (orderAmount >= 1000) {
            return 0;
        }
        return storeFee;
    }

    @Override
    public String getEstimatedDays() {
        return "2-3 工作天";
    }

    @Override
    public String getName() {
        return "超商取貨";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int calculateShippingFee(int orderAmount, double weightKg) {
        return 0; // 自取免運費
    }

    @Override
    public String getEstimatedDays() {
        return "當日可取";
    }

    @Override
    public String getName() {
        return "門市自取";
    }
}

class DeliveryOrderService {
    private final DeliveryMethod deliveryMethod;

    public DeliveryOrderService(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod == null ? new SelfPickup() : deliveryMethod;
    }

    public void printDeliverySummary(String orderId, int orderAmount, double weightKg) {
        int fee = deliveryMethod.calculateShippingFee(orderAmount, weightKg);
        int total = orderAmount + fee;
        System.out.printf("[%s] 方式: %-6s | 商品: NT$%5d | 運費: NT$%3d | 總額: NT$%5d | 預估: %s%n",
                orderId, deliveryMethod.getName(), orderAmount, fee, total, deliveryMethod.getEstimatedDays());
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        DeliveryOrderService homeService = new DeliveryOrderService(new HomeDelivery());
        DeliveryOrderService storeService = new DeliveryOrderService(new ConvenienceStoreDelivery());
        DeliveryOrderService pickupService = new DeliveryOrderService(new SelfPickup());

        System.out.println("================ 多方式配送系統 ================\n");

        homeService.printDeliverySummary("ORD-001", 1200, 6.5);
        homeService.printDeliverySummary("ORD-002", 2000, 3.0);
        storeService.printDeliverySummary("ORD-003", 800, 1.2);
        storeService.printDeliverySummary("ORD-004", 1100, 2.0);
        pickupService.printDeliverySummary("ORD-005", 500, 0.5);
    }
}