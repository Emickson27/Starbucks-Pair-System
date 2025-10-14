import java.util.Scanner;
import java.util.List;

public class CLIMenu {

    private Scanner scanner;
    private List<Drink> drinkMenu;
    public CLIMenu() {
        scanner = new Scanner(System.in);
    }
    

    public void showMainMenu(){
        String choice;
        do{
            System.out.println("=== Starbucks Menu ===");
            System.out.println("1. View Full Menu");
            System.out.println("2. View Menu by Type");
            System.out.println("3.Place an Order");
            System.out.println("4. View Today Sales");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            
            switch(choice){
                case "1":
                    printMenu(drinkMenu);
                    break;
                case "2":
                    System.out.print("Enter drink type (coffee, tea, refresher, frappuccino, seasonal): ");
                    String type = scanner.nextLine();
                    printMenuByType(drinkMenu, type);
                    break;
                case "3":
                    //Order Implementation
                    break;
                case "4":
                    //Sales Implementation
                    break;
                case "5":
                    System.out.println("Exiting... Thank you!");
                    break;
            }
        }while(!choice.equals("5"));
    }


    public void printMenuByType(List<Drink> drinks, String type){
        if (drinks == null || drinks.isEmpty()){
            System.out.println("No drinks found in the menu.");
            return;
        }
        System.out.println("\n==========  STARBUCKS MENU  ==========\n");
        for (Drink d : drinks) {
            if (d.drinkType == type.toLowerCase()){
                System.out.println(d);

            }

    }System.out.println("\n========================================\n");}

    // 🧾 Print a single drink
    public void printItem(Drink drink) {
        if (drink == null) {
            System.out.println("No drink to display.");
            return;
        }
        System.out.println("---------------------------------");
        System.out.println("Drink Name : " + drink.getDrinkName());
        System.out.println("Type       : " + drink.getDrinkType());
        System.out.println("Size       : " + drink.getSize());
        System.out.println("Price      : $" + drink.getPrice());
        System.out.println("---------------------------------");
    }

    // Print the full menu
    public void printMenu(List<Drink> drinks) {
        if (drinks == null || drinks.isEmpty()) {
            System.out.println("No drinks found in the menu.");
            return;
        }

        System.out.println("\n==========  STARBUCKS MENU  ==========\n");
        for (Drink d : drinks) {
            System.out.println(d);
        }
        System.out.println("\n========================================\n");
    }
}