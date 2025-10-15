import java.util.*;
import java.util.stream.Collectors;

public class SalesTracker {
    private final List<Order> completedOrders;
    private final List<Drink> menuItems;

    public SalesTracker(List<Drink> menuItems) {
        this.completedOrders = new ArrayList<>();
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void addCompletedOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (!order.isCompleted()) {
            throw new IllegalArgumentException("Order must be completed before adding to sales");
        }
        completedOrders.add(order);
    }

    public SalesSummary generateDailySummary() {
        return new SalesSummary(
            calculateTotalDrinksSold(),
            calculateTotalRevenue(),
            findMostPopularDrink(),
            getUniqueDrinkTypes(),
            findUnsoldDrinks()
        );
    }

    private int calculateTotalDrinksSold() {
        return completedOrders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToInt(item -> item.getQuantity())
                .sum();
    }

    private double calculateTotalRevenue() {
        return completedOrders.stream()
                .mapToDouble(Order::calculateTotal)
                .sum();
    }

    private String findMostPopularDrink() {
        Map<String, Integer> drinkCounts = new HashMap<>();

        for (Order order : completedOrders) {
            for (Order.OrderItem item : order.getItems()) {
                String key = item.getDrink().getDrinkName() + " (" + item.getDrink().getSize() + ")";
                drinkCounts.merge(key, item.getQuantity(), Integer::sum);
            }
        }

        return drinkCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }

    private Set<String> getUniqueDrinkTypes() {
        return completedOrders.stream()
                .flatMap(order -> order.getItems().stream())
                .map(item -> item.getDrink().getDrinkType())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private List<String> findUnsoldDrinks() {
        Set<String> soldDrinkKeys = completedOrders.stream()
                .flatMap(order -> order.getItems().stream())
                .map(item -> item.getDrink().getDrinkName() + "|" + item.getDrink().getSize())
                .collect(Collectors.toSet());

        return menuItems.stream()
                .filter(drink -> !soldDrinkKeys.contains(drink.getDrinkName() + "|" + drink.getSize()))
                .map(drink -> drink.getDrinkName() + " (" + drink.getSize() + ")")
                .collect(Collectors.toList());
    }

    public List<Order> getCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    public static class SalesSummary {
        private final int totalDrinksSold;
        private final double totalRevenue;
        private final String mostPopularDrink;
        private final Set<String> uniqueDrinkTypes;
        private final List<String> unsoldDrinks;

        public SalesSummary(int totalDrinksSold, double totalRevenue,
                          String mostPopularDrink, Set<String> uniqueDrinkTypes,
                          List<String> unsoldDrinks) {
            this.totalDrinksSold = totalDrinksSold;
            this.totalRevenue = totalRevenue;
            this.mostPopularDrink = mostPopularDrink;
            this.uniqueDrinkTypes = uniqueDrinkTypes;
            this.unsoldDrinks = unsoldDrinks;
        }

        // Getters
        public int getTotalDrinksSold() { return totalDrinksSold; }
        public double getTotalRevenue() { return totalRevenue; }
        public String getMostPopularDrink() { return mostPopularDrink; }
        public Set<String> getUniqueDrinkTypes() { return uniqueDrinkTypes; }
        public List<String> getUnsoldDrinks() { return unsoldDrinks; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== DAILY SALES SUMMARY ===\n");
            sb.append("Total Drinks Sold: ").append(totalDrinksSold).append("\n");
            sb.append("Total Revenue: $").append(totalRevenue).append("\n");
            sb.append("Most Popular Drink: ").append(mostPopularDrink).append("\n");
            sb.append("Unique Drink Types Sold: ").append(uniqueDrinkTypes.size()).append("\n");
            sb.append("Unsold Drinks: ").append(unsoldDrinks.size()).append("\n");

            if (!unsoldDrinks.isEmpty()) {
                sb.append("Unsold items:\n");
                for (String drink : unsoldDrinks) {
                    sb.append("  - ").append(drink).append("\n");
                }
            }

            return sb.toString();
        }
    }
}