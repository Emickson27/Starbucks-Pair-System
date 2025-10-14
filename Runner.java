import java.util.*;
public class Runner {
     public static void main(String[] args) {
        FileLoader fileloader = new FileLoader();
        

        List<Drink> drinkMenu = fileloader.loadDrinksFromCSV("menu.csv");
        fileloader.printMenu(drinkMenu);
    }
}
