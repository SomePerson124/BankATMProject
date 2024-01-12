import java.util.Scanner;

public class ATM {

    private Scanner scan;
    private Customer customer;
    private Account account;

    public ATM() {
        scan = new Scanner(System.in);
        customer = null;
    }

    public void start() {
        welcome();
    }

    private void welcome() {
        System.out.println("Welcome");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your pin: ");
        int pin = scan.nextInt();
        scan.nextLine();
        customer = new Customer(name, pin);
    }

    private void assignAccounts() {
        account = new Account("savings", 0, customer);
    }

}