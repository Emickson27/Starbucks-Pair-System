public class Refresher extends Drink {

    public Refresher(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice();
    }

    @Override
    public String getDisplayLabel() {
        return getDrinkType() + ": " + getDrinkName();
    }
}
