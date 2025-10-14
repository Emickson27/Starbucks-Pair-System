import java.util.*;
public class Runner {

    final boolean isRunning = false;
     public static void main(String[] args) {
        FileLoader fileloader = new FileLoader();
        CLIMenu menu = new CLIMenu();
        

        List<Drink> drinkMenu = fileloader.loadDrinksFromCSV("menu.csv");
        menu.printMenu(drinkMenu);
        menu.showMainMenu();
    }

    public boolean turnOnSystem(boolean isRunning){
        isRunning = true;
        return isRunning;
    }
    public boolean shutdownSystem(boolean isRunning){
        isRunning = false;
        return isRunning;
    }

}
