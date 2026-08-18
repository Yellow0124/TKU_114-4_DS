class Account {
    private final String accountId;
    private final String owner;
    private int balance;

    public Account(String accountId, String owner, int initialBalance) {
        this.accountId = (accountId == null || accountId.isBlank()) ? "UNKNOWN" : accountId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = Math.max(0, initialBalance);
    }

    public boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("[%s] 戶名: %-8s | 餘額: NT$%5d", accountId, owner, balance);
    }
}

class TransferService {
    public static boolean transfer(Account source, Account target, int amount) {
        if (source == null) {
            System.out.println("轉帳失敗：來源帳戶為 null！");
            return false;
        }
        if (target == null) {
            System.out.println("轉帳失敗：目標帳戶為 null！");
            return false;
        }

        if (source == target) {
            System.out.printf("轉帳失敗：來源帳戶與目標帳戶為同一實體 (%s)！%n", source.getAccountId());
            return false;
        }

        if (amount <= 0) {
            System.out.printf("轉帳失敗：轉帳金額必須大於 0 (傳入: NT$%d)！%n", amount);
            return false;
        }

        if (source.getBalance() < amount) {
            System.out.printf("轉帳失敗：來源帳戶餘額不足！(欲轉出: NT$%d, 當前餘額: NT$%d)%n",
                    amount, source.getBalance());
            return false;
        }

        boolean withdrawSuccess = source.withdraw(amount);
        if (!withdrawSuccess) {
            System.out.println("轉帳失敗：扣款異常！");
            return false;
        }
        target.deposit(amount);

        System.out.printf("轉帳成功：從 [%s] 轉出 NT$%d 至 [%s]%n",
                source.getAccountId(), amount, target.getAccountId());
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("ACC-101", "Amy", 2000);
        Account accB = new Account("ACC-102", "Ben", 500);

        System.out.println(">>> 1. 帳戶初始狀態：");
        System.out.println("  " + accA);
        System.out.println("  " + accB);
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 2. 測試正常轉帳情境 (NT$ 800)：");
        TransferService.transfer(accA, accB, 800);
        System.out.println("  轉帳後狀態：");
        System.out.println("  " + accA + " (預期餘額: 1200)");
        System.out.println("  " + accB + " (預期餘額: 1300)");
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 3. 測試餘額不足情境 (NT$ 1500)：");
        TransferService.transfer(accA, accB, 1500);
        System.out.println("  轉帳失敗後狀態（雙方應維持原狀）：");
        System.out.println("  " + accA + " (維持: 1200)");
        System.out.println("  " + accB + " (維持: 1300)");
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 4. 測試同帳戶轉帳情境 (accA 轉至 accA)：");
        TransferService.transfer(accA, accA, 300);
        System.out.println("  轉帳失敗後狀態：");
        System.out.println("  " + accA + " (維持: 1200)");
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 5. 測試目標帳戶為 null 情境：");
        TransferService.transfer(accA, null, 200);
        System.out.println("  轉帳失敗後狀態：");
        System.out.println("  " + accA + " (維持: 1200)");
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 6. 測試不合法金額 (NT$ -100 與 NT$ 0)：");
        TransferService.transfer(accA, accB, -100);
        TransferService.transfer(accA, accB, 0);
        System.out.println("----------------------------------------------------------------------\n");

        System.out.println(">>> 7. 最終帳戶狀態彙整：");
        System.out.println("  " + accA);
        System.out.println("  " + accB);

        System.out.println("\n======================================================================");
    }
}
