import java.util.Scanner;

public class ATM {

    private Scanner scan;
    private Customer customer;
    private Account savings;
    private Account checking;

    public ATM() {
        scan = new Scanner(System.in);
        customer = null;
    }

    public void start() {
        welcome();
        assignAccounts();
        checkPin();
        mainMenu();
    }

    private void welcome() {
        System.out.println("Welcome");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
        customer = new Customer(name, pin);
    }

    private void assignAccounts() {
        savings = new Account("savings", 0, customer);
        checking = new Account("checking", 0, customer);
    }

    private void checkPin() {
        System.out.print("Please enter your PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
        while (customer.getPin() != pin) {
            System.out.println("Incorrect Pin");
            System.out.print("Please enter your PIN: ");
            pin = scan.nextInt();
            scan.nextLine();
        }
    }

    private void mainMenu() {
        int option = 0;
        while (option != 7) {
            System.out.println("1. Withdraw money");
            System.out.println("2. Deposit money");
            System.out.println("3. Transfer money between accounts");
            System.out.println("4. Get account balances");
            System.out.println("5. Get transaction history");
            System.out.println("6. Change PIN");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();
            while (option > 7 || option < 1) {
                System.out.println("Invalid Option");
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();
            }
        }
        System.out.println("Thank You For Your Business");
    }

}