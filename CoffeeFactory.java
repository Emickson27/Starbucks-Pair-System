public class CoffeeFactory implements DrinkFactory {
    @Override
    public Drink createDrink(String name, String drinkType, String size, double price) {
        return new Coffee(name, drinkType, size, price);
    }
}
