public class RefresherFactory implements DrinkFactory{

    @Override
   public Drink createDrink(
         String drinkname, String drinkType, String size, double price
   ){
      return new Refresher(drinkname, drinkType, size, price);
   }
    
}
