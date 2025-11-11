
import java.util.*;

public class PromotionEngine {

    /**
     * Evaluate available promotions and return the best discount amount
     * (absolute dollars) for the given order. Only one promotion (the one that
     * gives max discount) is applied.
     */
    public static double calculateBestDiscount(Order order) {
        if (order == null) {
            return 0.0;
        }

        double subtotal = order.calculateSubtotal(); // includes add-ons
        int totalDrinks = order.getItems().stream().mapToInt(Order.OrderItem::getQuantity).sum();

        // 1) Bulk promotion: 10% off if >4 total drinks
        double bulkDiscount = (totalDrinks > 4) ? subtotal * 0.10 : 0.0;

        // 2) Category happy hour: 20% off teas when Seasonal.isHappyHour() is ON
        double categoryDiscount = 0.0;
        if (Seasonal.isHappyHour()) {
            double teaSubtotal = order.getItems().stream()
                    .filter(it -> it.getDrink().getDrinkType().equalsIgnoreCase("tea"))
                    .mapToDouble(it -> it.getUnitPrice() * it.getQuantity())
                    .sum();

            categoryDiscount = teaSubtotal * 0.20;
        }

        // 3) Buy-3-get-1 free (for every 4 same drink-name items -> 1 free).
        // Free item is the cheapest base-size among that drink name (exclude add-ons).
        double buyNgetMDiscount = 0.0;
        Map<String, List<Order.OrderItem>> groups = new HashMap<>();
        for (Order.OrderItem item : order.getItems()) {
            String key = item.getDrink().getDrinkName();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }

        for (Map.Entry<String, List<Order.OrderItem>> entry : groups.entrySet()) {
            List<Order.OrderItem> groupItems = entry.getValue();
            int totalQty = groupItems.stream().mapToInt(Order.OrderItem::getQuantity).sum();
            int freeCount = totalQty / 4; // every 4 items -> 1 free
            if (freeCount <= 0) {
                continue;
            }

            // find minimum base drink price among sizes for this drink name (exclude add-ons)
            double minBasePrice = groupItems.stream()
                    .mapToDouble(it -> it.getDrink().calculatePrice())
                    .min()
                    .orElse(0.0);

            buyNgetMDiscount += freeCount * minBasePrice;
        }

        // Choose the largest discount
        double best = Math.max(bulkDiscount, Math.max(categoryDiscount, buyNgetMDiscount));
        // Don't allow discount to exceed subtotal
        if (best > subtotal) {
            best = subtotal;
        }
        // Round to cents
        return Math.round(best * 100.0) / 100.0;
    }
}
