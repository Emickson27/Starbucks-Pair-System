import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLoader {

    private final Map<String, DrinkFactory> factoryMap;

    public FileLoader() {
        factoryMap = new HashMap<>();
        factoryMap.put("coffee", new CoffeeFactory());
        factoryMap.put("tea", new TeaFactory());
        factoryMap.put("refresher", new RefresherFactory());
        factoryMap.put("frappuccino", new FrappuccinoFactory());
        factoryMap.put("seasonal", new SeasonalFactory());
    }

    public List<Drink> loadDrinksFromCSV(String filePath) {
        List<Drink> drinks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip CSV header
                    continue;
                }

                String[] values = line.split(",");
                if (values.length < 4) continue;

                String drinkName = values[0].trim();
                String drinkType = values[1].trim();
                String size = values[2].trim();
                double price = Double.parseDouble(values[3].trim());

                // look up factory
                DrinkFactory factory = factoryMap.get(drinkType.toLowerCase());
                if (factory != null) {
                    Drink drink = factory.createDrink(drinkName, drinkType, size, price);
                    drinks.add(drink);
                } else {
                    System.err.println("⚠️ Unknown drink type: " + drinkType);
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }

        return drinks;
    }

    // 🧾 Print a single drink
    public void printItem(Drink drink) {
        if (drink == null) {
            System.out.println("No drink to display.");
            return;
        }
        System.out.println("---------------------------------");
        System.out.println("Drink Name : " + drink.getDrinkName());
        System.out.println("Type       : " + drink.getDrinkType());
        System.out.println("Size       : " + drink.getSize());
        System.out.println("Price      : $" + drink.getPrice());
        System.out.println("---------------------------------");
    }

    // Print the full menu
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
}
