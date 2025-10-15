public class Seasonal extends Drink {
    private static boolean isHappyHour = false;

    public Seasonal(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        double price = getBasePrice();

        // Seasonal drinks get 20% off during happy hour
        if (isHappyHour) {
            price *= 0.8;
        }

        return price;
    }

    @Override
    public String getDisplayLabel() {
        String label = getDrinkType() + ": " + getDrinkName();
        if (isHappyHour) {
            label += " (Happy Hour!)";
        }
        return label;
    }

    // Static method to set happy hour status
    public static void setHappyHour(boolean happyHour) {
        isHappyHour = happyHour;
    }

    public static boolean isHappyHour() {
        return isHappyHour;
    }
}
