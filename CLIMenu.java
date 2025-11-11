import java.util.*;

public class CLIMenu implements StarbucksOperations {

    private final Scanner scanner = new Scanner(System.in);
    private List<Drink> drinkMenu;
    private SalesTracker salesTracker;
    private int nextOrderNumber = 1;

    // 5 predefined add-ons
    private final List<AddOn> availableAddOns = List.of(
        new AddOn("Extra Shot", 0.75),
        new AddOn("Soy Milk", 0.50),
        new AddOn("Almond Milk", 0.60),
        new AddOn("Whipped Cream", 0.40),
        new AddOn("Caramel Drizzle", 0.65)
    );

    public CLIMenu() {
        salesTracker = new SalesTracker(new ArrayList<>());
    }

    // ===== INTERFACE IMPLEMENTATION =====

    @Override
    public List<AddOn> getAvailableAddOns() {
        return availableAddOns;
    }

    @Override
    public boolean validateAddOnChoice(int index) {
        return index >= 1 && index <= availableAddOns.size();
    }

    @Override
    public boolean validateQuantity(int qty) {
        return qty > 0 && qty <= 50;
    }

    @Override
    public boolean validatePrice(double price) {
        return price >= 0.0 && price <= 20.0;
    }

    @Override
    public double applyPromotions(Order order) {
        return PromotionEngine.calculateBestDiscount(order);
    }

    // ===== MENU LOGIC =====

    public void setDrinkMenu(List<Drink> drinkMenu) {
        this.drinkMenu = drinkMenu;
        this.salesTracker = new SalesTracker(drinkMenu);
    }

    public void showMainMenu() {
        String choice;
        do {
            System.out.println("\n=== Starbucks Menu ===");
            System.out.println("1. View Full Menu");
            System.out.println("2. View Menu by Type");
            System.out.println("3. Place an Order");
            System.out.println("4. View Today Sales");
            System.out.println("5. Toggle Happy Hour");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> printMenu(drinkMenu);
                case "2" -> {
                    System.out.print("Enter drink type (coffee, tea, refresher, frappuccino, seasonal): ");
                    String type = scanner.nextLine();
                    printMenuByType(drinkMenu, type);
                }
                case "3" -> placeOrder();
                case "4" -> viewSalesSummary();
                case "5" -> toggleHappyHour();
                case "6" -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("6"));
    }

    // ===== MENU DISPLAY =====

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

    public void printMenu(List<Drink> drinks) {
        if (drinks == null || drinks.isEmpty()) {
            System.out.println("No drinks found in the menu.");
            return;
        }
        System.out.println("\n==========  STARBUCKS MENU  ==========\n");
        for (Drink d : drinks) System.out.println(d);
        System.out.println("\n========================================\n");
    }

    // ===== ORDER FLOW =====

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
            System.out.println("ERROR: Phone number cannot be empty.");
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
                    System.out.println("Order is empty. Add some items or cancel.");
                    continue;
                }
                break;
            }

            System.out.print("Enter size (Tall/Grande/Venti): ");
            String size = scanner.nextLine().trim();

            Drink selectedDrink = findDrinkByNameAndSize(drinkName, size);
            if (selectedDrink == null) {
                System.out.println("Invalid drink or size. Try again.");
                continue;
            }

            int quantity = 0;
            while (true) {
                System.out.print("Enter quantity: ");
                try {
                    quantity = Integer.parseInt(scanner.nextLine().trim());
                    if (validateQuantity(quantity)) break;
                    System.out.println("Invalid quantity. Please enter a positive number.");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }

            System.out.println("\nAvailable Add-ons:");
            for (int i = 0; i < availableAddOns.size(); i++) {
                AddOn a = availableAddOns.get(i);
                System.out.printf("%d. %s - $%.2f%n", i + 1, a.getName(), a.getPrice());
            }

            List<AddOn> chosenAddOns = new ArrayList<>();
            while (true) {
                System.out.print("Enter add-on numbers separated by commas (0 for none): ");
                String addOnInput = scanner.nextLine().trim();
                if (addOnInput.equals("0") || addOnInput.isEmpty()) break;

                boolean validInput = true;
                String[] indices = addOnInput.split(",");
                for (String s : indices) {
                    try {
                        int idx = Integer.parseInt(s.trim());
                        if (validateAddOnChoice(idx))
                            chosenAddOns.add(availableAddOns.get(idx - 1));
                        else {
                            System.out.println("Unknown add-on option: " + idx);
                            validInput = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, please use numbers only.");
                        validInput = false;
                    }
                }
                if (validInput) break;
            }

            order.addItem(selectedDrink, quantity, chosenAddOns);
            System.out.println("Added " + quantity + "x " + drinkName + " (" + size + ")");
            if (!chosenAddOns.isEmpty()) {
                System.out.print("Add-ons: ");
                chosenAddOns.forEach(a -> System.out.print(a.getName() + " "));
                System.out.println();
            }
        }

        order.completeOrder();
        salesTracker.addCompletedOrder(order);

        System.out.println("\n========== ORDER SUMMARY ==========");
        System.out.println(order);

        double subtotal = order.calculateSubtotal();
        double discount = applyPromotions(order);
        double tax = (subtotal - discount) * 0.10;
        double total = subtotal - discount + tax;

        System.out.printf("%nSubtotal: $%.2f%nDiscount: -$%.2f%nTax: $%.2f%nTotal: $%.2f%n",
                subtotal, discount, tax, total);

        List<String> addOnNames = order.getItems().stream()
                .flatMap(it -> it.getAddOns().stream().map(AddOn::getName))
                .distinct().toList();

        UserReceipt.writeCustomerReceipt(customer, order.getItems(), addOnNames, total, tax, nextOrderNumber);
        System.out.println("SUCCESS: Order completed successfully!");
    }

    // ===== SALES SUMMARY =====

    private void viewSalesSummary() {
        System.out.println("\n========== SALES SUMMARY ==========");
        SalesTracker.SalesSummary summary = salesTracker.generateDailySummary();
        System.out.println(summary);

        // Add-on and promo analytics
        Map<String, Integer> addOnCount = new HashMap<>();
        Map<String, Double> addOnRevenue = new HashMap<>();
        double totalDiscountGiven = 0.0;
        Map<String, Double> categoryRevenue = new HashMap<>();
        Map<String, Integer> categorySold = new HashMap<>();

        for (Order o : salesTracker.getCompletedOrders()) {
            totalDiscountGiven += applyPromotions(o);
            for (Order.OrderItem it : o.getItems()) {
                String type = it.getDrink().getDrinkType();
                double rev = it.getTotalPrice();
                categoryRevenue.merge(type, rev, Double::sum);
                categorySold.merge(type, it.getQuantity(), Integer::sum);

                for (AddOn a : it.getAddOns()) {
                    addOnCount.merge(a.getName(), it.getQuantity(), Integer::sum);
                    addOnRevenue.merge(a.getName(), a.getPrice() * it.getQuantity(), Double::sum);
                }
            }
        }

        System.out.println("\nTop 3 Add-ons by Quantity:");
        addOnCount.entrySet().stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
            .limit(3)
            .forEach(e -> System.out.printf("  %s: %d sold%n", e.getKey(), e.getValue()));

        System.out.println("\nTop 3 Add-ons by Revenue:");
        addOnRevenue.entrySet().stream()
            .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
            .limit(3)
            .forEach(e -> System.out.printf("  %s: $%.2f%n", e.getKey(), e.getValue()));

        System.out.println("\nPromotion Impact:");
        System.out.printf("  Total Discounts Given Today: $%.2f%n", totalDiscountGiven);

        System.out.println("\nCategory Breakdown (Items Sold / Revenue):");
        for (String cat : categoryRevenue.keySet()) {
            System.out.printf("  %s: %d items, $%.2f%n",
                    cat, categorySold.get(cat), categoryRevenue.get(cat));
        }
    }

    // ===== HELPERS =====

    private Drink findDrinkByNameAndSize(String name, String size) {
        return drinkMenu.stream()
                .filter(drink -> drink.matches(name, size))
                .findFirst()
                .orElse(null);
    }

    private void toggleHappyHour() {
        boolean currentStatus = Seasonal.isHappyHour();
        Seasonal.setHappyHour(!currentStatus);
        if (Seasonal.isHappyHour())
            System.out.println("Happy Hour is now ON! 20% off seasonal drinks!");
        else
            System.out.println("Happy Hour is now OFF!");
    }
}

