
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserReceipt {

    private static final String RECEIPT_FOLDER = "customer_receipts";

    public static void writeCustomerReceipt(Customer customer, Drink drink, List<String> addOns,
                                            double totalPrice, double tax, int orderNumber) {

        double finalAmount = totalPrice + tax;
        String confirmation = "CNF-" + System.currentTimeMillis();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        StringBuilder receipt = new StringBuilder();
        receipt.append("=== Customer Receipt ===\n");
        receipt.append("Confirmation #: ").append(confirmation).append("\n");
        receipt.append("Order #: ").append(orderNumber).append("\n");
        receipt.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        receipt.append("Customer: ").append(customer.getName()).append("\n");
        receipt.append("Phone: ").append(customer.getPhoneNumber()).append("\n\n");
        receipt.append("Item: ").append(drink.getSize()).append(" ").append(drink.getDrinkName()).append("\n");

        if (addOns != null && !addOns.isEmpty()) {
            receipt.append("Add-ons: ").append(String.join(", ", addOns)).append("\n");
        }

        receipt.append("\nSubtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
        receipt.append("Tax:      $").append(String.format("%.2f", tax)).append("\n");
        receipt.append("-------------------------\n");
        receipt.append("Total:    $").append(String.format("%.2f", finalAmount)).append("\n\n");
        receipt.append("Thank you for your purchase!\n");

        File folder = new File(RECEIPT_FOLDER);
        if (!folder.exists()) folder.mkdirs();

        String safeName = customer.getName().replaceAll("[^a-zA-Z0-9\\-_]", "_");
        String fileName = safeName + "_" + timestamp + ".txt";
        File outFile = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write(receipt.toString());
            System.out.println("Receipt saved to: " + outFile.getPath());
        } catch (IOException e) {
            System.err.println("Error writing receipt: " + e.getMessage());
        }
    }
}
