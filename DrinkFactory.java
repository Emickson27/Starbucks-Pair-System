// The abstract factory interface
public interface DrinkFactory {
    Drink createDrink(String name,String drinkType, String size, double price);
}
