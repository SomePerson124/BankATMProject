import java.util.Scanner;

public class ATM {

    private Scanner scan;
    private Customer customer;
    private Account savings;
    private Account checking;
    private TransactionHistory transactionHistory;

    public ATM() {
        scan = new Scanner(System.in);
        customer = null;
        transactionHistory = new TransactionHistory();
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
        System.out.print("Enter a PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
        customer = new Customer(name, pin);
        System.out.println();
        System.out.println("You are now a Customer!");
    }

    private void assignAccounts() {
        savings = new Account("savings", 0, customer);
        checking = new Account("checking", 0, customer);
        System.out.println("You have been assigned two accounts: a savings account and a checking account");
        System.out.println();
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
            System.out.println();
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
            System.out.println();
            while (option > 7 || option < 1) {
                System.out.println("Invalid Option");
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();
            }
            processOption(option);
            if (option != 7) {
                System.out.print("Do you want to do anything else? (y/n): ");
                String response = scan.nextLine();
                response = response.toLowerCase();
                while (!response.equals("y") && !response.equals("n")) {
                    System.out.print("Please enter valid response: ");
                    response = scan.nextLine();
                    response = response.toLowerCase();
                }
                if (response.equals("y")) {
                    checkPin();
                }
                if (response.equals("n")) {
                    option = 7;
                    System.out.println();
                }
            }
        }
        System.out.println("Thank You For Being A Customer");
    }

    private void processOption(int option) {
        if (option == 1 || option == 2) {
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            System.out.print("Choose an account: ");
            int account = scan.nextInt();
            System.out.println();
            while (account != 1 && account != 2) {
                System.out.println("Invalid option");
                System.out.print("Choose an account: ");
                account = scan.nextInt();
                System.out.println();
            }
            scan.nextLine();
            if (option == 1) {
                System.out.println("This ATM only gives out $5 and $20 bills");
                System.out.print("Enter the amount you want to withdraw: ");
                int amount = scan.nextInt();
                scan.nextLine();
                while (amount % 5 != 0) {
                    System.out.println("Can't withdraw this amount");
                    System.out.print("Enter the amount you want to withdraw: ");
                    amount = scan.nextInt();
                    scan.nextLine();
                }
                System.out.println();
                if (account == 1) {
                    String summary = "Withdrew $" + amount + " from savings";
                    if (savings.getCurrentBalance() < amount) {
                        System.out.println("Insufficient Funds!");
                        System.out.println("You do not have enough money in your account to withdraw that amount");
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                        System.out.println();
                    } else {
                        System.out.print("How many $20 bills do you want to receive? ");
                        int twenties = scan.nextInt();
                        scan.nextLine();
                        while (20 * twenties > amount) {
                            System.out.println("You can't receive this amount. Choose a lower number of $20 bills");
                            System.out.print("How many $20 bills do you want to receive? ");
                            twenties = scan.nextInt();
                            scan.nextLine();
                        }
                        System.out.println();
                        System.out.println(savings.numberOfBills(amount, twenties));
                        savings.withdraw(amount);
                        System.out.println("Withdrawal was successful");
                        System.out.println(summary);
                        System.out.println(savings.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                    }
                } else {
                    String summary = "Withdrew $" + amount + " from checking";
                    if (checking.getCurrentBalance() < amount) {
                        System.out.println("Insufficient Funds!");
                        System.out.println("You do not have enough money in your account to withdraw that amount");
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                        System.out.println();
                    } else {
                        System.out.println("How many $20 bills do you want to receive? ");
                        int twenties = scan.nextInt();
                        scan.nextLine();
                        while (20 * twenties > amount) {
                            System.out.println("You can't receive this amount. Choose a lower number of $20 bills");
                            System.out.print("How many $20 bills do you want to receive? ");
                            twenties = scan.nextInt();
                            scan.nextLine();
                        }
                        System.out.println();
                        System.out.println(checking.numberOfBills(amount, twenties));
                        checking.withdraw(amount);
                        System.out.println("Withdrawal was successful");
                        System.out.println(summary);
                        System.out.println(checking.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                    }
                }
            } else {
                System.out.print("Enter the amount you want to deposit: ");
                double amount = scan.nextDouble();
                scan.nextLine();
                System.out.println();
                if (account == 1) {
                    String summary = "Deposited $" + amount + " into savings";
                    if (amount > 0) {
                        savings.deposit(amount);
                        System.out.println("Deposit was successful");
                        System.out.println(summary);
                        System.out.println(savings.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                    } else {
                        System.out.println("Deposit must be positive");
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                    }
                } else {
                    String summary = "Deposited $" + amount + " into checking";
                    if (amount > 0) {
                        checking.deposit(amount);
                        System.out.println("Deposit was successful");
                        System.out.println(summary);
                        System.out.println(checking.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                    } else {
                        System.out.println("Deposit must be positive");
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                    }
                }
            }
        }
        if (option == 3) {
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            System.out.print("Which account is the transfer FROM: ");
            int account = scan.nextInt();
            while (account != 1 && account != 2) {
                System.out.println("Invalid option");
                System.out.print("Which account is the transfer FROM: ");
                account = scan.nextInt();
            }
            System.out.print("How much are you going to transfer: ");
            double amount = scan.nextDouble();
            scan.nextLine();
            System.out.println();
            if (account == 1) {
                String summary = "Transferred $" + amount + " from " + savings.getAccountName() + " to " + checking.getAccountName();
                if (savings.getCurrentBalance() >= amount) {
                    savings.transfer(amount, checking);
                    System.out.println("Transfer was successful");
                    System.out.println(summary);
                    System.out.println(savings.balanceMessage());
                    System.out.println(checking.balanceMessage());
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                } else {
                    System.out.println("Transfer amount must not exceed current balance on account");
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                }
            } else {
                String summary = "Transferred $" + amount + " from " + checking.getAccountName() + " to " + savings.getAccountName();
                if (checking.getCurrentBalance() >= amount) {
                    checking.transfer(amount, savings);
                    System.out.println("Transfer was successful");
                    System.out.println(summary);
                    System.out.println(savings.balanceMessage());
                    System.out.println(checking.balanceMessage());
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                } else {
                    System.out.println("Transfer amount must not exceed current balance on account");
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                }
            }
        }
        if (option == 4) {
            System.out.println(savings.balanceMessage());
            System.out.println(checking.balanceMessage());
            String summary = "Viewed account balances";
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
        if (option == 5) {
            System.out.println(transactionHistory.getTransactionHistory());
            String summary = "Viewed Transaction History";
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
        if (option == 6) {
            System.out.print("Enter your new PIN: ");
            int newPIN = scan.nextInt();
            scan.nextLine();
            customer.setPin(newPIN);
            String summary = "Changed PIN";
            System.out.println(summary);
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
    }

}