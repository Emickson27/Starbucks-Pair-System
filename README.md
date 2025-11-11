# Starbucks Ordering System DAY1

A command-line interface (CLI) application that simulates a Starbucks ordering system. This Java-based application allows users to browse a menu, place orders, track sales, and manage special promotions like Happy Hour.

## Features

- **Menu Management**: Browse the full menu or filter by drink type (Coffee, Tea, Refresher, Frappuccino, Seasonal)
- **Order Placement**: Create customer orders with multiple items and automatic bulk discounts
- **Sales Tracking**: Monitor daily sales with detailed analytics including revenue, popular items, and unsold products
- **Happy Hour**: Toggle special pricing for seasonal drinks (20% discount)
- **CSV Menu Import**: Load menu items dynamically from a CSV file
- **Price Calculations**: Automatic pricing with special rules for different drink types

## Project Structure

### Core Classes

- **`Runner.java`**: Main entry point of the application
- **`CLIMenu.java`**: Handles user interaction and menu navigation
- **`FileLoader.java`**: Loads drink menu from CSV file

### Domain Models

- **`Drink.java`**: Abstract base class for all drink types
- **`Coffee.java`**: Standard coffee drinks
- **`Tea.java`**: Tea-based beverages
- **`Refresher.java`**: Refresher drinks
- **`Frappuccino.java`**: Blended drinks (includes $0.50 upcharge)
- **`Seasonal.java`**: Seasonal drinks with Happy Hour discount support

### Order Management

- **`Customer.java`**: Stores customer information (name, phone number)
- **`Order.java`**: Manages order items and calculates totals with bulk discount (10% off for 4+ drinks)
- **`OrderItem.java`**: Represents individual items in an order

### Design Patterns

- **`DrinkFactory.java`**: Factory interface for creating drink objects
- **`CoffeeFactory.java`**, **`TeaFactory.java`**, **`RefresherFactory.java`**, **`FrappuccinoFactory.java`**, **`SeasonalFactory.java`**: Concrete factory implementations
- **`DrinkFactoryDecider.java`**: Factory selector (alternative implementation)
- **`SalesTracker.java`**: Tracks and analyzes sales data

## Requirements

- Java Development Kit (JDK) 8 or higher
- A terminal or command prompt

## Installation

1. Clone or download this repository
2. Navigate to the project directory
3. Ensure `menu.csv` is in the same directory as the Java files

## Usage

### Compile the Project

```bash
javac *.java
```

### Run the Application

```bash
java Runner
```

### Main Menu Options

1. **View Full Menu**: Display all available drinks
2. **View Menu by Type**: Filter menu by drink category
3. **Place an Order**: Create a new customer order
4. **View Today Sales**: Display sales analytics
5. **Toggle Happy Hour**: Enable/disable seasonal drink discounts
6. **Exit**: Close the application

### Placing an Order

1. Enter customer name and phone number
2. Add drinks by specifying:
   - Drink name (e.g., "Pumpkin Spice Latte")
   - Size (Tall, Grande, or Venti)
   - Quantity
3. Type "checkout" when finished
4. The system automatically applies a 10% discount for orders with 4+ drinks

## Menu Configuration

The menu is loaded from `menu.csv` with the following format:

```csv
Drink Name,Drink Type,Size,Price
Caffe Americano,Coffee,Tall,2.95
Pumpkin Spice Latte,Seasonal,Grande,4.95
```

### Supported Drink Types

- `Coffee`: Standard coffee drinks
- `Tea`: Tea-based beverages
- `Refresher`: Fruit refreshers
- `Frappuccino`: Blended drinks (automatic $0.50 upcharge)
- `Seasonal`: Limited-time offerings (eligible for Happy Hour discount)

## Pricing Rules

- **Base Price**: As listed in menu.csv
- **Frappuccino Upcharge**: +$0.50 to base price
- **Happy Hour Discount**: 20% off seasonal drinks (when enabled)
- **Bulk Discount**: 10% off total when ordering 4+ drinks

## Sales Analytics

The sales tracking system provides:

- Total drinks sold
- Total revenue
- Most popular drink
- Unique drink types sold
- List of unsold menu items

## Design Patterns Used

### Factory Pattern

The application uses the Factory design pattern to create different drink types, allowing for flexible object creation and easy addition of new drink categories.

### Encapsulation

All domain models use proper encapsulation with private fields and public getters, ensuring data integrity.

### Template Method Pattern

The `Drink` abstract class defines the template for drink behavior, with subclasses implementing specific pricing logic.

## Example Workflow

```
=== Starbucks Menu ===
1. View Full Menu
2. View Menu by Type
3. Place an Order
4. View Today Sales
5. Toggle Happy Hour
6. Exit
Enter your choice: 3

========== PLACE ORDER ==========
Enter customer name: John Doe
Enter customer phone number: 555-1234

Adding items to order. Type 'checkout' when finished.
Enter drink name (or 'checkout'): Pumpkin Spice Latte
Enter size (Tall/Grande/Venti): Grande
Enter quantity: 2
SUCCESS: Added 2x Pumpkin Spice Latte (Grande) to order - $9.90

Enter drink name (or 'checkout'): checkout

========== ORDER SUMMARY ==========
Order #1
Customer: John Doe
Items:
  Pumpkin Spice Latte x2 - $9.90
Subtotal: $9.90
Total: $9.90

SUCCESS: Order completed successfully!
```

## Error Handling

The application includes robust error handling for:

- Invalid menu selections
- Missing or duplicate drinks in CSV
- Empty customer information
- Invalid quantities
- File loading errors

## Future Enhancements

- Database integration for persistent storage
- User authentication and order history
- Custom drink modifications (milk type, extra shots, etc.)
- Loyalty rewards program
- Receipt printing functionality
- Multi-location support

## Contributing

This project was created as an educational exercise demonstrating object-oriented programming principles, design patterns, and CLI application development in Java.

## License

This project is provided as-is for educational purposes.

---

**Note**: This is a simulation project and is not affiliated with Starbucks Corporation.
