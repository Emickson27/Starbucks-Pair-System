public class Seasonal extends Drink {
    private static boolean isHappyHour = false;

    public Seasonal(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        double price = getBasePrice();

        if (isHappyHour) {
            price *= 0.8;
        }

        return price;
    }

    @Override
    public String getDisplayLabel() {
        String displayLabel = getDrinkType() + ": " + getDrinkName();
        if (isHappyHour) {
            displayLabel += " (Happy Hour!)";
        }
        return displayLabel;
    }

    // Static method to set happy hour status
    public static void setHappyHour(boolean happyHour) {
        isHappyHour = happyHour;
    }

    public static boolean isHappyHour() {
        return isHappyHour;
    }
}
