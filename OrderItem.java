public class OrderItem {
    private final Drink drink;
    private final int quantity;

    public OrderItem(Drink drink, int quantity) {
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.drink = drink;
        this.quantity = quantity;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return drink.calculatePrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d - $%.2f",
                           drink.getDrinkName(),
                           quantity,
                           getTotalPrice());
    }
}