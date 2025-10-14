public class Coffee extends Drink{

    public Coffee(String drinkname, String drinkType, String size, double price) {
        super(drinkname, drinkType, size, price);
    }   

     @Override
    public String toString() {
        return drinkType + ": " + drinkName + " (" + size + ") - $" + price;
    }

}
