public class DrinkFactoryDecider {
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
