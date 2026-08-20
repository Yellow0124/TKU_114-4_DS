abstract class EmployeeBase {
    private final String id;
    private final String name;

    EmployeeBase(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        System.out.println("EmployeeBase constructor: id=" + this.id + ", name=" + this.name);
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    abstract int calculatePay();
}

class FullTimeWorker extends EmployeeBase {
    private final int monthlySalary;

    FullTimeWorker(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        System.out.println("FullTimeWorker constructor: salary=" + this.monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeWorker extends EmployeeBase {
    private final int hours;
    private final int hourlyRate;

    PartTimeWorker(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
        System.out.println("PartTimeWorker constructor: hours=" + this.hours + ", rate=" + this.hourlyRate);
    }

    @Override
    int calculatePay() {
        return hours * hourlyRate;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("--- 建立 FullTimeWorker ---");
        EmployeeBase fullTime = new FullTimeWorker("E101", "Amy", 50000);
        System.out.println("pay=" + fullTime.calculatePay());

        System.out.println("\n--- 建立 PartTimeWorker (含負數邊界值測試) ---");
        EmployeeBase partTime = new PartTimeWorker("E102", "Ben", -10, 200);
        System.out.println("pay=" + partTime.calculatePay());
    }
}