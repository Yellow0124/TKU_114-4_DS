import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class RepoProduct {
    private final String id;
    private final String name;
    private final int price;

    RepoProduct(String id, String name, int price) {
        this.id = (id == null || id.isBlank()) ? "P000" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.price = Math.max(0, price);
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RepoProduct that))
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %-10s (NT$%4d)", id, name, price);
    }
}

class Repository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public T remove(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public int size() {
        return items.size();
    }

    public void printAll(String repositoryName) {
        System.out.println("=== " + repositoryName + " (Total: " + items.size() + ") ===");
        if (items.isEmpty()) {
            System.out.println("  (Empty Repository)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  [%d] %s%n", i, items.get(i));
            }
        }
        System.out.println();
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("================ Generic Repository System ================\n");

        Repository<String> stringRepo = new Repository<>();
        stringRepo.add("Java");
        stringRepo.add("Data Structures");
        stringRepo.add("Algorithms");

        stringRepo.printAll("字串倉儲 (Repository<String>)");
        System.out.println("取得 index 1: " + stringRepo.get(1));

        stringRepo.remove("Data Structures");
        System.out.println("移除 \"Data Structures\" 後：");
        stringRepo.printAll("字串倉儲 (Repository<String>)");

        Repository<RepoProduct> productRepo = new Repository<>();
        RepoProduct p1 = new RepoProduct("P101", "Keyboard", 1500);
        RepoProduct p2 = new RepoProduct("P102", "Mouse", 800);
        RepoProduct p3 = new RepoProduct("P103", "Monitor", 6000);

        productRepo.add(p1);
        productRepo.add(p2);
        productRepo.add(p3);

        productRepo.printAll("商品倉儲 (Repository<RepoProduct>)");

        productRepo.remove(new RepoProduct("P102", "Mouse", 800));
        System.out.println("移除商品 P102 後：");
        productRepo.printAll("商品倉儲 (Repository<RepoProduct>)");

        System.out.println("越界存取 index 99: " + productRepo.get(99));
    }
}