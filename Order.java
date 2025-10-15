import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    public static class OrderItem {
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
            return drink.getDrinkName() + " x" + quantity + " - $" + getTotalPrice();
        }
    }
    private final List<Order.OrderItem> items;
    private final Customer customer;
    private final int orderNumber;
    private boolean isCompleted;

    public Order(Customer customer, int orderNumber) {
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.orderNumber = orderNumber;
        this.items = new ArrayList<>();
        this.isCompleted = false;
    }

    public void addItem(Drink drink, int quantity) {
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        items.add(new Order.OrderItem(drink, quantity));
    }

    public void removeItem(Drink drink, int quantity) {
        if (drink == null || quantity <= 0) {
            return;
        }

        items.removeIf(item ->
            item.getDrink().getDrinkName().equals(drink.getDrinkName()) &&
            item.getDrink().getSize().equals(drink.getSize()) &&
            item.getQuantity() <= quantity
        );
    }

    public double calculateSubtotal() {
        return items.stream()
                .mapToDouble(item -> item.getDrink().calculatePrice() * item.getQuantity())
                .sum();
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();

        // Apply bulk discount if more than 3 total drinks
        int totalDrinks = items.stream().mapToInt(item -> item.getQuantity()).sum();
        if (totalDrinks > 3) {
            subtotal *= 0.9; // 10% discount
        }

        return subtotal;
    }

    public void completeOrder() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public List<Order.OrderItem> getItems() {
        return new ArrayList<>(items); // Return defensive copy
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderNumber).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Items:\n");

        for (Order.OrderItem item : items) {
            sb.append("  ").append(item).append("\n");
        }

        sb.append("Subtotal: $" + calculateSubtotal() + "\n");
        sb.append("Total: $" + calculateTotal() + "\n");

        return sb.toString();
    }
}