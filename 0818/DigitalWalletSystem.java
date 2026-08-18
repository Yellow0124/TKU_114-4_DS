class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int successfulTransactionCount;

    public DigitalWallet(String walletId, String owner, int initialBalance) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = Math.max(0, initialBalance);
        this.successfulTransactionCount = 0;
    }

    public boolean deposit(int amount) {
        if (amount <= 0) {
            System.out.printf("儲值失敗：金額必須大於 0 (傳入: %d)%n", amount);
            return false;
        }
        balance += amount;
        successfulTransactionCount++;
        System.out.printf("儲值成功：+$%d | 當前餘額: $%d%n", amount, balance);
        return true;
    }

    public boolean pay(int amount) {
        if (amount <= 0) {
            System.out.printf("付款失敗：金額必須大於 0 (傳入: %d)%n", amount);
            return false;
        }
        if (amount > balance) {
            System.out.printf("付款失敗：餘額不足！(欲扣款: $%d, 當前餘額: $%d)%n", amount, balance);
            return false;
        }
        balance -= amount;
        successfulTransactionCount++;
        System.out.printf("付款成功：-$%d | 當前餘額: $%d%n", amount, balance);
        return true;
    }

    public boolean refund(int amount) {
        if (amount <= 0) {
            System.out.printf("退款失敗：金額必須大於 0 (傳入: %d)%n", amount);
            return false;
        }
        balance += amount;
        successfulTransactionCount++;
        System.out.printf("退款成功：+$%d | 當前餘額: $%d%n", amount, balance);
        return true;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() {
        return balance;
    }

    public int getSuccessfulTransactionCount() {
        return successfulTransactionCount;
    }

    @Override
    public String toString() {
        return String.format("DigitalWallet{walletId='%s', owner='%s', balance=$%d, transactions=%d}",
                walletId, owner, balance, successfulTransactionCount);
    }
}

public class DigitalWalletSystem {

}
