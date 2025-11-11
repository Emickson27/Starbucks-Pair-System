public class AddOn {

    private String name;
    private final double price;

    public AddOn(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("AddOn name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("AddOn price cannot be negative");
        }
        this.name = name.trim();
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
