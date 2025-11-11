
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLIMenu {

    private Scanner scanner;
    private List<Drink> drinkMenu;
    private SalesTracker salesTracker;
    private int nextOrderNumber;

    // Available add-ons (make up prices)
    private static final List<AddOn> AVAILABLE_ADDONS = Arrays.asList(
            new AddOn("extra shot", 0.75),
            new AddOn("vanilla syrup", 0.50),
            new AddOn("almond milk", 0.60),
            new AddOn("soy milk", 0.50),
            new AddOn("caramel drizzle", 0.40)
    );

    public CLIMenu() {
        scanner = new Scanner(System.in);
        salesTracker = new SalesTracker(new ArrayList<>());
        nextOrderNumber = 1;
    }

    public void setDrinkMenu(List<Drink> drinkMenu) {
        this.drinkMenu = drinkMenu;
        this.salesTracker = new SalesTracker(drinkMenu);
    }

    public void showMainMenu() {
        String choice;
        do {
            System.out.println("=== Starbucks Menu ===");
            System.out.println("1. View Full Menu");
            System.out.println("2. View Menu by Type");
            System.out.println("3. Place an Order");
            System.out.println("4. View Today Sales");
            System.out.println("5. Toggle Happy Hour");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printMenu(drinkMenu);
                    break;
                case "2":
                    System.out.print("Enter drink type (coffee, tea, refresher, frappuccino, seasonal): ");
                    String type = scanner.nextLine();
                    printMenuByType(drinkMenu, type);
                    break;
                case "3":
                    placeOrder();
                    break;
                case "4":
                    viewSalesSummary();
                    break;
                case "5":
                    toggleHappyHour();
                    break;
                case "6":
                    System.out.println("Exiting... Thank you!");
                    break;
            }
        } while (!choice.equals("6"));
    }

    public void printMenuByType(List<Drink> drinks, String type) {
        if (drinks == null || drinks.isEmpty()) {
            System.out.println("No drinks found in the menu.");
            return;
        }
        System.out.println("\n==========  STARBUCKS MENU  ==========\n");
        for (Drink d : drinks) {
            if (d.getDrinkType().equalsIgnoreCase(type)) {
                System.out.println(d);

            }

        }
        System.out.println("\n========================================\n");
    }

    public void printItem(Drink drink) {
        if (drink == null) {
            System.out.println("No drink to display.");
            return;
        }
        System.out.println("---------------------------------");
        System.out.println("Drink Name : " + drink.getDrinkName());
        System.out.println("Type       : " + drink.getDrinkType());
        System.out.println("Size       : " + drink.getSize());
        System.out.println("Price      : $" + drink.calculatePrice());
        System.out.println("---------------------------------");
    }

    public void printMenu(List<Drink> drinks) {
        if (drinks == null || drinks.isEmpty()) {
            System.out.println("No drinks found in the menu.");
            return;
        }

        System.out.println("\n==========  STARBUCKS MENU  ==========\n");
        for (Drink d : drinks) {
            System.out.println(d);
        }
        System.out.println("\n========================================\n");
    }

    private void placeOrder() {
        if (drinkMenu == null || drinkMenu.isEmpty()) {
            System.out.println("ERROR: No drinks available in the menu.");
            return;
        }

        System.out.println("\n========== PLACE ORDER ==========");
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("ERROR: Customer name cannot be empty.");
            return;
        }

        System.out.print("Enter customer phone number: ");
        String phoneNumber = scanner.nextLine().trim();

        if (phoneNumber.isEmpty()) {
            System.out.println("ERROR: Phone number cannot be empty");
            return;
        }

        Customer customer = new Customer(customerName, phoneNumber);
        Order order = new Order(customer, nextOrderNumber++);

        System.out.println("\nAdding items to order. Type 'checkout' when finished.");

        while (true) {
            System.out.print("Enter drink name (or 'checkout'): ");
            String drinkName = scanner.nextLine().trim();

            if (drinkName.equalsIgnoreCase("checkout")) {
                if (order.getItems().isEmpty()) {
                    System.out.println("WARNING: Order is empty. Add some items or cancel.");
                    continue;
                }
                break;
            }

            System.out.print("Enter size (Tall/Grande/Venti): ");
            String size = scanner.nextLine().trim();

            System.out.print("Enter quantity: ");
            String quantityStr = scanner.nextLine().trim();

            try {
                int quantity = Integer.parseInt(quantityStr);
                Drink selectedDrink = findDrinkByNameAndSize(drinkName, size);

                if (selectedDrink != null) {
                    // Ask for add-ons (strongly typed)
                    List<AddOn> selectedAddOns = new ArrayList<>();
                    System.out.print("Would you like add-ons? (y/n): ");
                    String wantAddons = scanner.nextLine().trim();
                    if (wantAddons.equalsIgnoreCase("y")) {
                        System.out.println("Available add-ons:");
                        for (int i = 0; i < AVAILABLE_ADDONS.size(); i++) {
                            AddOn a = AVAILABLE_ADDONS.get(i);
                            System.out.println((i + 1) + ". " + a.getName() + " ($" + a.getPrice() + ")");
                        }
                        System.out.print("Enter add-on numbers separated by commas (or leave empty): ");
                        String addonInput = scanner.nextLine().trim();
                        if (!addonInput.isEmpty()) {
                            String[] tokens = addonInput.split(",");
                            for (String t : tokens) {
                                try {
                                    int idx = Integer.parseInt(t.trim()) - 1;
                                    if (idx >= 0 && idx < AVAILABLE_ADDONS.size()) {
                                        selectedAddOns.add(AVAILABLE_ADDONS.get(idx));
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        }
                    }

                    order.addItem(selectedDrink, quantity, selectedAddOns);
                    double addonsTotal = selectedAddOns.stream().mapToDouble(AddOn::getPrice).sum() * quantity;
                    System.out.println("SUCCESS: Added " + quantity + "x " + selectedDrink.getDrinkName() + " (" + size + ")"
                            + (selectedAddOns.isEmpty() ? "" : " + " + selectedAddOns)
                            + " to order - $" + String.format("%.2f", selectedDrink.calculatePrice() * quantity + addonsTotal));
                } else {
                    System.out.println("ERROR: Drink '" + drinkName + "' in size '" + size + "' not found in menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid quantity. Please enter a number.");
            }
        }

        order.completeOrder();
        salesTracker.addCompletedOrder(order);

        System.out.println("\n========== ORDER SUMMARY ==========");
        System.out.println(order);

        // calculate totals for receipt
        double total = order.calculateTotal();
        double tax = total * 0.10;
        List<String> addOns = new ArrayList<>(); // if you don’t track add-ons yet

        UserReceipt.writeCustomerReceipt(
            customer,
            order.getItems().get(0).getDrink(), // or adjust if you have multiple drinks
            addOns,
            total,
            tax,
            nextOrderNumber
);

System.out.println("SUCCESS: Order completed successfully!");
    }

    private void viewSalesSummary() {
        System.out.println("\n========== SALES SUMMARY ==========");
        SalesTracker.SalesSummary summary = salesTracker.generateDailySummary();
        System.out.println(summary);

        
    }

    private Drink findDrinkByNameAndSize(String name, String size) {
        return drinkMenu.stream()
                .filter(drink -> drink.matches(name, size))
                .findFirst()
                .orElse(null);
    }

    private void toggleHappyHour() {
        boolean currentStatus = Seasonal.isHappyHour();
        Seasonal.setHappyHour(!currentStatus);

        System.out.println("\n========== HAPPY HOUR ==========");
        if (Seasonal.isHappyHour()) {
            System.out.println("Happy Hour is now ON!");
            System.out.println("Seasonal drinks are now 20% off!");
        } else {
            System.out.println("Happy Hour is now OFF!");
            System.out.println("Seasonal drinks are back to regular price.");
        }
        System.out.println("===============================\n");
    }
}
