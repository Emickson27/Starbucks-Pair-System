public abstract class Drink {
    protected String drinkName;
    protected String drinkType;
    protected String size;
    protected double price;

    public Drink(String drinkName,String drinkType, String size, double price) {
        this.drinkName = drinkName;
        this.drinkType = drinkType;
        this.size = size;
        this.price = price;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return drinkType + ": " + drinkName + " (" + size + ") - $" + price;
    }
}
