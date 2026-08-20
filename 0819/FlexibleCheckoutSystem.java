// 1. 計價策略介面
interface PricingPolicy {
    String getPolicyName();

    int calculateFinalPrice(int originalPrice);
}

// 原價策略
class StandardPricePolicy implements PricingPolicy {
    @Override
    public String getPolicyName() {
        return "原價計費";
    }

    @Override
    public int calculateFinalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

// VIP 85折策略
class VipDiscountPolicy implements PricingPolicy {
    @Override
    public String getPolicyName() {
        return "VIP 85折";
    }

    @Override
    public int calculateFinalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

// 滿 2000 折 300 策略 (Threshold Discount)
class ThresholdDiscountPolicy implements PricingPolicy {
    @Override
    public String getPolicyName() {
        return "滿2000折300";
    }

    @Override
    public int calculateFinalPrice(int originalPrice) {
        int safePrice = Math.max(0, originalPrice);
        return safePrice >= 2000 ? safePrice - 300 : safePrice;
    }
}

// 2. 通知管道介面
interface NotificationChannel {
    String getChannelName();

    boolean send(String receiver, String message);
}

// Email 通知管道
class EmailChannelService implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "Email";
    }

    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("  [EMAIL] " + receiver.trim() + " -> " + message);
        return true;
    }
}

// SMS 簡訊通知管道
class SmsChannelService implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "SMS";
    }

    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.trim().matches("^09\\d{8}$")) {
            return false;
        }
        System.out.println("  [SMS]   " + receiver.trim() + " -> " + message);
        return true;
    }
}

// Console 控制台通知管道
class ConsoleChannelService implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "Console";
    }

    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) {
            return false;
        }
        System.out.println("  [CONSOLE] " + receiver.trim() + " -> " + message);
        return true;
    }
}

// 3. 不可變結帳結果物件
final class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean notificationSent;
    private final String policyName;
    private final String channelName;

    public CheckoutResult(String orderId, int originalPrice, int finalPrice,
            boolean notificationSent, String policyName, String channelName) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationSent = notificationSent;
        this.policyName = policyName;
        this.channelName = channelName;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public boolean isNotificationSent() {
        return notificationSent;
    }

    @Override
    public String toString() {
        return String.format("[%s] 原價: NT$%5d | 實付: NT$%5d (%s) | 通知: %-7s [%s]",
                orderId, originalPrice, finalPrice, policyName, (notificationSent ? "成功" : "失敗"), channelName);
    }
}

// 4. 結帳服務協調整合
class FlexibleCheckoutService {
    private final PricingPolicy pricingPolicy;
    private final NotificationChannel notificationChannel;

    public FlexibleCheckoutService(PricingPolicy pricingPolicy, NotificationChannel notificationChannel) {
        this.pricingPolicy = (pricingPolicy == null) ? new StandardPricePolicy() : pricingPolicy;
        this.notificationChannel = (notificationChannel == null) ? new ConsoleChannelService() : notificationChannel;
    }

    public CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        String safeOrderId = (orderId == null || orderId.isBlank()) ? "ORD-UNKNOWN" : orderId.trim();
        int safePrice = Math.max(0, originalPrice);

        int finalPrice = pricingPolicy.calculateFinalPrice(safePrice);
        String message = "訂單成立 " + safeOrderId + "，結帳金額: NT$" + finalPrice;

        boolean sent = notificationChannel.send(receiver, message);

        return new CheckoutResult(safeOrderId, safePrice, finalPrice, sent,
                pricingPolicy.getPolicyName(), notificationChannel.getChannelName());
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        System.out.println("================ 通知與費用系統擴充 ================\n");

        PricingPolicy standard = new StandardPricePolicy();
        PricingPolicy vip = new VipDiscountPolicy();
        PricingPolicy threshold = new ThresholdDiscountPolicy();

        NotificationChannel email = new EmailChannelService();
        NotificationChannel sms = new SmsChannelService();
        NotificationChannel console = new ConsoleChannelService();

        FlexibleCheckoutService[] services = {
                new FlexibleCheckoutService(standard, email),
                new FlexibleCheckoutService(standard, sms),
                new FlexibleCheckoutService(vip, email),
                new FlexibleCheckoutService(vip, console),
                new FlexibleCheckoutService(threshold, sms),
                new FlexibleCheckoutService(threshold, console)
        };

        String[][] testCases = {
                { "ORD-001", "1500", "amy@example.com" },
                { "ORD-002", "800", "0912345678" },
                { "ORD-003", "2000", "invalid_email" },
                { "ORD-004", "3000", "AdminTerminal" },
                { "ORD-005", "2500", "0988776655" },
                { "ORD-006", "1800", "CashierDesk" }
        };

        System.out.println(">>> 執行 6 種策略與管道組合結帳：");
        for (int i = 0; i < services.length; i++) {
            String orderId = testCases[i][0];
            int price = Integer.parseInt(testCases[i][1]);
            String receiver = testCases[i][2];

            CheckoutResult result = services[i].checkout(orderId, price, receiver);
            System.out.println(result);
        }

        System.out.println("\n================================================================================");
    }
}