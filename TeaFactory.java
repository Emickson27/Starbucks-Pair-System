public class TeaFactory implements DrinkFactory {
    @Override
    public Drink createDrink(String name, String drinkType, String size, double price) {
        return new Tea(name, drinkType, size, price);
    }
}
