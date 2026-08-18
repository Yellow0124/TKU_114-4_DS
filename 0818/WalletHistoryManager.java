final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    public WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = (type == null || type.isBlank()) ? "UNKNOWN" : type.trim();
        this.amount = Math.max(0, amount);
        this.balanceAfter = Math.max(0, balanceAfter);
    }

    public int getSequence() {
        return sequence;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return String.format("  #%d [%-12s] 金額: NT$%5d | 交易後餘額: NT$%5d",
                sequence, type, amount, balanceAfter);
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    public boolean deposit(int amount) {
        if (amount <= 0 || isFull()) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    public boolean pay(int amount) {
        if (amount <= 0 || amount > balance || isFull()) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    public boolean refund(int amount) {
        if (amount <= 0 || isFull()) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    public boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this || amount <= 0) {
            return false;
        }
        if (amount > this.balance) {
            return false;
        }
        if (this.isFull() || target.isFull()) {
            return false;
        }

        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    public WalletTransaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }

    public int totalByType(String type) {
        if (type == null || type.isBlank()) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getType().equalsIgnoreCase(type.trim())) {
                sum += transactions[i].getAmount();
            }
        }
        return sum;
    }

    public boolean isFull() {
        return transactionCount >= transactions.length;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
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

    public int getTransactionCount() {
        return transactionCount;
    }

    public void printStatement() {
        System.out.println("======================================================================");
        System.out.printf("🏦 錢包對帳單 | 編號: %-6s | 持有人: %-8s | 當前餘額: NT$%5d%n",
                walletId, owner, balance);
        System.out.printf("   交易筆數: %d / %d (上限)%n", transactionCount, transactions.length);
        System.out.println("----------------------------------------------------------------------");
        if (transactionCount == 0) {
            System.out.println("  (尚無任何交易紀錄)");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println(transactions[i]);
            }
        }
        System.out.println("======================================================================\n");
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet walletA = new DigitalWallet("W101", "Amy", 6);
        DigitalWallet walletB = new DigitalWallet("W102", "Ben", 4);

        System.out.println(">>> 1. 執行基礎交易操作 (儲值、付款、退款)：");
        System.out.println("Amy 儲值 2000: " + (walletA.deposit(2000) ? "成功" : "失敗"));
        System.out.println("Amy 付款 500 : " + (walletA.pay(500) ? "成功" : "失敗"));
        System.out.println("Amy 退款 100 : " + (walletA.refund(100) ? "成功" : "失敗"));
        System.out.println("Ben 儲值 800  : " + (walletB.deposit(800) ? "成功" : "失敗"));
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 2. 測試 transferTo 跨錢包轉帳 (Amy 轉 NT$600 給 Ben)：");
        boolean transferRes = walletA.transferTo(walletB, 600);
        System.out.println("轉帳結果: " + (transferRes ? "成功" : "失敗"));
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 3. 測試 findTransaction(sequence) 依序號搜尋：");
        int targetSeq = 2;
        WalletTransaction tx = walletA.findTransaction(targetSeq);
        System.out.println("Amy 查詢第 " + targetSeq + " 筆交易:\n" + (tx != null ? tx : "  找不到該筆交易"));

        int notFoundSeq = 99;
        WalletTransaction txNotFound = walletA.findTransaction(notFoundSeq);
        System.out.println(
                "Amy 查詢第 " + notFoundSeq + " 筆交易:\n" + (txNotFound != null ? txNotFound : "  找不到該筆交易 (回傳 null)"));
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 4. 測試 totalByType(type) 統計特定類型金額：");
        System.out.println("Amy 儲值總額 (DEPOSIT)     : NT$" + walletA.totalByType("DEPOSIT"));
        System.out.println("Amy 付款總額 (PAY)         : NT$" + walletA.totalByType("PAY"));
        System.out.println("Amy 轉出總額 (TRANSFER_OUT): NT$" + walletA.totalByType("TRANSFER_OUT"));
        System.out.println("Ben 轉入總額 (TRANSFER_IN) : NT$" + walletB.totalByType("TRANSFER_IN"));
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 5. 測試容量已滿防禦情境 (walletB 上限 4 筆)：");
        walletB.pay(100);
        walletB.deposit(50);
        System.out.println("Ben 目前交易筆數: " + walletB.getTransactionCount() + " (已達容量上限)");

        System.out.println("嘗試對已滿的 Ben 錢包再儲值 200: " + (walletB.deposit(200) ? "成功" : "失敗 (拒絕修改餘額)"));
        System.out.println("Amy 嘗試轉帳 100 給已滿的 Ben   : " + (walletA.transferTo(walletB, 100) ? "成功" : "失敗 (拒絕修改餘額)"));
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 6. 輸出兩錢包完整對帳單 (Statement)：\n");
        walletA.printStatement();
        walletB.printStatement();
    }
}
