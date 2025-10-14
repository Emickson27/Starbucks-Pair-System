public class FrappuccinoFactory implements DrinkFactory{

    @Override
   public Drink createDrink(
         String drinkname, String drinkType, String size, double price
   ){
      return new Frappuccino(drinkname, drinkType, size, price);
   }
    
}
