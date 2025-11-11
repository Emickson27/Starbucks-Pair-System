
import java.util.ArrayList;
import java.util.List;

public class OrderItem {

    private final Drink drink;
    private final int quantity;
    private final List<AddOn> addOns;

    public OrderItem(Drink drink, int quantity) {
        this(drink, quantity, new ArrayList<>());
    }

    public OrderItem(Drink drink, int quantity, List<AddOn> addOns) {
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.drink = drink;
        this.quantity = quantity;
        this.addOns = addOns == null ? new ArrayList<>() : new ArrayList<>(addOns);
    }

    public Drink getDrink() {
        return drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<AddOn> getAddOns() {
        return new ArrayList<>(addOns);
    }

    public double getUnitPrice() {
        double unit = drink.calculatePrice();
        for (AddOn a : addOns) {
            unit += a.getPrice();
        }
        return Math.round(unit * 100.0) / 100.0;
    }

    public double getTotalPrice() {
        return Math.round(getUnitPrice() * quantity * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        StringBuilder label = new StringBuilder();
        label.append(drink.getDrinkName()).append(" (").append(drink.getSize()).append(")");
        for (AddOn a : addOns) {
            label.append(" + ").append(a.getName());
        }
        return String.format("%s x%d - $%.2f", label.toString(), quantity, getTotalPrice());
    }
}
