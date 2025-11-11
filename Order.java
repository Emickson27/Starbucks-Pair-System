
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    public static class OrderItem {

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

        // Unit price including add-ons (for a single unit)
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

        public String getLabel() {
            StringBuilder sb = new StringBuilder();
            sb.append(drink.getDrinkName()).append(" (").append(drink.getSize()).append(")");
            for (AddOn a : addOns) {
                sb.append(" + ").append(a.getName());
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            return String.format("%s x%d - $%.2f", getLabel(), quantity, getTotalPrice());
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

    // Overload: add without add-ons
    public void addItem(Drink drink, int quantity) {
        addItem(drink, quantity, new ArrayList<>());
    }

    public void addItem(Drink drink, int quantity, List<AddOn> addOns) {
        if (drink == null) {
            throw new IllegalArgumentException("Drink cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        items.add(new Order.OrderItem(drink, quantity, addOns));
    }

    public void removeItem(Drink drink, int quantity) {
        if (drink == null || quantity <= 0) {
            return;
        }

        items.removeIf(item
                -> item.getDrink().getDrinkName().equals(drink.getDrinkName())
                && item.getDrink().getSize().equals(drink.getSize())
                && item.getQuantity() <= quantity
        );
    }

    // Subtotal includes add-ons
    public double calculateSubtotal() {
        double sum = items.stream()
                .mapToDouble(Order.OrderItem::getTotalPrice)
                .sum();
        return Math.round(sum * 100.0) / 100.0;
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();

        // Let PromotionEngine choose the best single promotion
        double discount = PromotionEngine.calculateBestDiscount(this);

        double total = subtotal - discount;
        if (total < 0) {
            total = 0;
        }
        return Math.round(total * 100.0) / 100.0;
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

        sb.append(String.format("Subtotal: $%.2f\n", calculateSubtotal()));
        sb.append(String.format("Total: $%.2f\n", calculateTotal()));

        return sb.toString();
    }
}
