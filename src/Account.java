public class Account {

    private String accountName;
    private double currentBalance;
    private Customer customer;

    public Account(String accountName, double currentBalance, Customer customer) {
        this.accountName = accountName;
        this.currentBalance = currentBalance;
        this.customer = customer;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void withdraw(int amount) {
        currentBalance -= amount;
    }

    public void deposit(double amount) {
        currentBalance += amount;
    }

    public void transfer(double amount, Account to) {
        currentBalance -= amount;
        to.currentBalance += amount;
    }

    public String numberOfBills(int amount, int twenties) {
        int fives = amount - twenties * 20;
        return "You will receive " + twenties + " $20 bills and " + fives + " $5 bills";
    }

    public String balanceMessage() {
        return accountName + " account: $" + currentBalance;
    }

}
