final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = walletId == null || walletId.isBlank() ? "UNKNOWN" : walletId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || transactionCount >= transactions.length) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this || amount <= 0) {
            return false;
        }

        if (amount > this.balance) {
            return false;
        }

        if (this.transactionCount >= this.transactions.length ||
                target.transactionCount >= target.transactions.length) {
            return false;
        }

        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner + " balance=" + balance);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println("  " + transactions[i]);
        }
    }
}

public class WalletTransactionSystem {
    public static void main(String[] args) {
        DigitalWallet walletA = new DigitalWallet("W001", "Amy", 6);
        DigitalWallet walletB = new DigitalWallet("W002", "Ben", 5);

        System.out.println("=== 基礎操作測試 ===");
        System.out.println("Amy 儲值 1000: " + walletA.deposit(1000));
        System.out.println("Amy 付款 250: " + walletA.pay(250));
        System.out.println("Amy 付款 900 (餘額不足應失敗): " + walletA.pay(900));
        System.out.println("Amy 退款 50: " + walletA.refund(50));

        System.out.println("\n=== 轉帳操作測試 ===");
        // 測試成功轉帳
        System.out.println("Amy 轉帳 300 給 Ben: " + walletA.transferTo(walletB, 300));

        // 測試失敗情境（轉給自己、金額 <= 0、餘額不足）
        System.out.println("Amy 轉帳給自己 (應失敗): " + walletA.transferTo(walletA, 100));
        System.out.println("Amy 轉帳 2000 給 Ben (餘額不足應失敗): " + walletA.transferTo(walletB, 2000));

        System.out.println("\n=== 雙方交易明細 ===");
        walletA.printStatement();
        System.out.println();
        walletB.printStatement();
    }
}