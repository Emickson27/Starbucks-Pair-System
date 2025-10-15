import java.util.Objects;

public abstract class Drink {
    private final String drinkName;
    private final String drinkType;
    private final String size;
    private final double basePrice;

    public Drink(String drinkName, String drinkType, String size, double basePrice) {
        this.drinkName = Objects.requireNonNull(drinkName, "Drink name cannot be null").trim();
        this.drinkType = Objects.requireNonNull(drinkType, "Drink type cannot be null").toLowerCase().trim();
        this.size = Objects.requireNonNull(size, "Size cannot be null").trim();
        this.basePrice = basePrice;

        if (this.drinkName.isEmpty()) {
            throw new IllegalArgumentException("Drink name cannot be empty");
        }
        if (this.drinkType.isEmpty()) {
            throw new IllegalArgumentException("Drink type cannot be empty");
        }
        if (this.size.isEmpty()) {
            throw new IllegalArgumentException("Size cannot be empty");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }

        validateSize(this.size);
    }

    public abstract double calculatePrice();
    public abstract String getDisplayLabel();

    @Override
    public final String toString() {
        return getDisplayLabel() + " (" + size + ") - $" + calculatePrice();
    }

    // Protected validation method for subclasses to override if needed
    protected void validateSize(String size) {
        // Default implementation - subclasses can override for specific validation
    }

    // Getters
    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getSize() {
        return size;
    }

    public double getBasePrice() {
        return basePrice;
    }

    // Utility method for finding drinks by name and size
    public boolean matches(String name, String size) {
        return drinkName.equalsIgnoreCase(name) && this.size.equalsIgnoreCase(size);
    }
}
