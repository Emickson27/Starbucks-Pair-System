import java.util.*;
public class Runner {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        CLIMenu menu = new CLIMenu();

        List<Drink> drinkMenu = fileLoader.loadDrinksFromCSV("menu.csv");
        menu.setDrinkMenu(drinkMenu);
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
