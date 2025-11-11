import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserReceipt {

    private static final String RECEIPT_FOLDER = "customer_receipts";

    public static void writeCustomerReceipt(Customer customer,
                                            List<Order.OrderItem> items,
                                            List<String> addOns,
                                            double totalPrice,
                                            double tax,
                                            int orderNumber) {

        double finalAmount = totalPrice + tax;
        String confirmation = "CNF-" + System.currentTimeMillis();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        StringBuilder receipt = new StringBuilder();
        receipt.append("=== Starbucks Customer Receipt ===\n");
        receipt.append("Confirmation #: ").append(confirmation).append("\n");
        receipt.append("Order #: ").append(orderNumber).append("\n");
        receipt.append("Date: ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        receipt.append("Customer: ").append(customer.getName()).append("\n");
        receipt.append("Phone: ").append(customer.getPhoneNumber()).append("\n\n");

        receipt.append("Items Ordered:\n");

        // Loop through each order item (each drink)
        for (Order.OrderItem item : items) {
            Drink drink = item.getDrink();
            double itemTotal = drink.calculatePrice() * item.getQuantity();

            receipt.append(" - ")
                   .append(item.getQuantity()).append("x ")
                   .append(drink.getSize()).append(" ")
                   .append(drink.getDrinkName())
                   .append(" @ $").append(String.format("%.2f", drink.calculatePrice()))
                   .append(" = $").append(String.format("%.2f", itemTotal))
                   .append("\n");

            // Show add-ons with names and prices
            if (addOns != null && !addOns.isEmpty()) {
                receipt.append("    Add-ons: ");
                for (int i = 0; i < addOns.size(); i++) {
                    String name = addOns.get(i);
                    double price = 0.0;

                    // Match add-on name to known price
                    switch (name.toLowerCase()) {
                        case "extra shot" -> price = 0.75;
                        case "soy milk" -> price = 0.50;
                        case "almond milk" -> price = 0.60;
                        case "whipped cream" -> price = 0.40;
                        case "caramel drizzle" -> price = 0.65;
                    }

                    receipt.append(name).append(" ($").append(String.format("%.2f", price)).append(")");
                    if (i < addOns.size() - 1) receipt.append(", ");
                }
                receipt.append("\n");
            }
            receipt.append("\n");
        }

        receipt.append("Subtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
        receipt.append("Tax:      $").append(String.format("%.2f", tax)).append("\n");
        receipt.append("------------------------------\n");
        receipt.append("Total:    $").append(String.format("%.2f", finalAmount)).append("\n\n");
        receipt.append("Thank you for your purchase!\n");

        // Create the folder if it doesn't exist
        File folder = new File(RECEIPT_FOLDER);
        if (!folder.exists()) folder.mkdirs();

        // Generate safe file name
        String safeName = customer.getName().replaceAll("[^a-zA-Z0-9\\-_]", "_");
        String fileName = safeName + "_" + timestamp + ".txt";
        File outFile = new File(folder, fileName);

        // Write to file
        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write(receipt.toString());
            System.out.println("Receipt saved to: " + outFile.getPath());
        } catch (IOException e) {
            System.err.println("Error writing receipt: " + e.getMessage());
        }
    }
}
