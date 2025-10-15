public class Frappuccino extends Drink {

    public Frappuccino(String drinkName, String drinkType, String size, double basePrice) {
        super(drinkName, drinkType, size, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() + 0.50;
    }

    @Override
    public String getDisplayLabel() {
        return getDrinkType() + ": " + getDrinkName();
    }
}
