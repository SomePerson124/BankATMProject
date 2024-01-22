// Class that represents a customer

public class Customer {

    // instance variables

    // name of the customer
    private String name;
    // pin that the customer gave
    private int pin;

    // constructor
    public Customer(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    // method to get the pin that the customer gave
    public int getPin() {
        return pin;
    }

    // method to get customer's name
    public String getName() {
        return name;
    }

    // method to set a new pin
    public void setPin(int newPin) {
        this.pin = newPin;
    }

}
