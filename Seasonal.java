public class Seasonal extends Drink{

    public Seasonal(String drinkname, String drinkType, String size, double price) {
        super(drinkname, drinkType, size, price);
    }   

     @Override
    public String toString() {
        return drinkType + ": " + drinkName + " (" + size + ") - $" + price;
    }
    
}
