public class Tea extends Drink {

    public Tea(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice(); // Tea uses base price as-is
    }

    @Override
    public String getDisplayLabel() {
        return getDrinkType() + ": " + getDrinkName();
    }
}
