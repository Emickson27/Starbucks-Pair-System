public class Refresher extends Drink {

    public Refresher(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice(); // Refresher uses base price as-is
    }

    @Override
    public String getDisplayLabel() {
        return getDrinkType() + ": " + getDrinkName();
    }
}
