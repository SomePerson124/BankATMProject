// Class that represents the accounts of a customer

public class Account {

    //instance variables

    // name of the account
    private String accountName;
    // current balance of the account
    private double currentBalance;
    // customer of the account
    private Customer customer;

    // constructor
    public Account(String accountName, double currentBalance, Customer customer) {
        this.accountName = accountName;
        this.currentBalance = currentBalance;
        this.customer = customer;
    }

    // method to get the current balance
    public double getCurrentBalance() {
        return currentBalance;
    }

    // method to get the name of the account
    public String getAccountName() {
        return accountName;
    }

    // method to withdraw money from the account
    public void withdraw(int amount) {
        currentBalance -= amount;
    }

    // method to deposit money to the account
    public void deposit(double amount) {
        currentBalance += amount;
    }

    // method to transfer money from account to another account
    public void transfer(double amount, Account to) {
        currentBalance -= amount;
        to.currentBalance += amount;
    }

    // method to determine amount of bills to give to the customer
    public String numberOfBills(int amount, int twenties) {
        int fives = (amount - twenties * 20) / 5;
        return "You will receive " + ConsoleUtility.BLUE + twenties + ConsoleUtility.RESET + " $20 bills and " + ConsoleUtility.BLUE + fives + ConsoleUtility.RESET + " $5 bills";
    }

    // method that returns a message about the balance of the account
    public String balanceMessage() {
        if (currentBalance == (int) currentBalance) {
            return accountName + " account: " + ConsoleUtility.YELLOW + "$" + currentBalance + "0" + ConsoleUtility.RESET;
        } else {
            return accountName + " account: " + ConsoleUtility.YELLOW + "$" + currentBalance + ConsoleUtility.RESET;
        }
    }

}
