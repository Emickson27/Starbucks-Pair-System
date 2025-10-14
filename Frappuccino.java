public class Frappuccino extends Drink{

    public Frappuccino(String drinkname, String drinkType, String size, double price) {
        super(drinkname, drinkType, size, price);
    }   

     @Override
    public String toString() {
        return drinkType + ": " + drinkName + " (" + size + ") - $" + price;
    }
    
}
