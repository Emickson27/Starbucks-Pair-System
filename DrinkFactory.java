// The abstract factory interface
public interface DrinkFactory {
    Drink createDrink(String name,String drinkType, String size, double price);

    /**
     * Static factory method to get the appropriate drink factory based on category
     */
    public static DrinkFactory getFactory(String drinkCategory) {
        switch (drinkCategory.toLowerCase()) {
            case "coffee":
                return new CoffeeFactory();
            case "tea":
                return new TeaFactory();
            case "frappuccino":
                return new FrappuccinoFactory();
            case "refresher":
                return new RefresherFactory();
            case "seasonal":
                return new SeasonalFactory();
            default:
                throw new IllegalArgumentException("Unknown drink category: " + drinkCategory);
        }
    }
}
