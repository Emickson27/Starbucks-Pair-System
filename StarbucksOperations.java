import java.util.List;

public interface StarbucksOperations {
    List<AddOn> getAvailableAddOns();
    boolean validateAddOnChoice(int index);
    boolean validateQuantity(int qty);
    boolean validatePrice(double price);
    double applyPromotions(Order order);
}


