import java.util.Objects;

public class Customer {
    private final String name;
    private final String phoneNumber;

    public Customer(String name, String phoneNumber) {
        this.name = Objects.requireNonNull(name, "Name cannot be null").trim();
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number cannot be null").trim();

        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (this.phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name + " (" + phoneNumber + ")";
    }
}