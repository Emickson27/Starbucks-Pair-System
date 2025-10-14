public class Tea extends Drink{

    public Tea(String drinkname, String drinkType, String size, double price) {
        super(drinkname, drinkType, size, price);
    }   

     @Override
    public String toString() {
        return drinkType + ": " + drinkName + " (" + size + ") - $" + price;
    }
}
