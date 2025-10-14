public class SeasonalFactory implements DrinkFactory{

    @Override
   public Drink createDrink(
         String drinkname, String drinkType, String size, double price
   ){
      return new Seasonal(drinkname, drinkType, size, price);
   }
    
}
