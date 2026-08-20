abstract class Employee {
    private final String id;
    private final String name;

    Employee(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    abstract int calculatePay();

    @Override
    public String toString() {
        return String.format("[%s] %-8s | 薪資: NT$%6d", id, name, calculatePay());
    }
}

class SalariedEmp extends Employee {
    private final int monthlySalary;
    private final int bonus;

    SalariedEmp(String id, String name, int monthlySalary, int bonus) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        this.bonus = Math.max(0, bonus);
    }

    @Override
    int calculatePay() {
        return monthlySalary + bonus;
    }
}

class HourlyEmp extends Employee {
    private final int hours;
    private final int hourlyRate;

    HourlyEmp(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
    }

    @Override
    int calculatePay() {
        return hours * hourlyRate;
    }
}

class CommissionEmp extends Employee {
    private final int baseSalary;
    private final int salesAmount;
    private final double commissionRate;

    CommissionEmp(String id, String name, int baseSalary, int salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0.0, commissionRate);
    }

    @Override
    int calculatePay() {
        return baseSalary + (int) (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
                new SalariedEmp("E101", "Amy", 50000, 5000),
                new HourlyEmp("E102", "Ben", 160, 220),
                new CommissionEmp("E103", "Cara", 30000, 400000, 0.08),
                new HourlyEmp("E104", "David", 120, 200)
        };

        int totalPayroll = 0;
        Employee highestPaid = employees[0];

        for (Employee emp : employees) {
            System.out.println(emp);
            int pay = emp.calculatePay();
            totalPayroll += pay;
            if (pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("全體薪資總額: NT$" + totalPayroll);
        System.out.println("最高薪資員工: " + highestPaid.getName() + " (NT$" + highestPaid.calculatePay() + ")");
    }
}