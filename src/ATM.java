// Class that contains the logic of the program

import java.util.Scanner;

public class ATM {

    // instance variables

    // Scanner object
    private Scanner scan;
    // Customer object
    private Customer customer;
    // Account object for savings account
    private Account savings;
    // Account object for checking account
    private Account checking;
    // TransactionHistory object
    private TransactionHistory transactionHistory;

    // constructor
    public ATM() {
        scan = new Scanner(System.in);
        customer = null;
        transactionHistory = new TransactionHistory();
    }

    // start method to run the program
    public void start() {
        welcome();
        assignAccounts();
        checkPin();
        mainMenu();
    }

    // welcome method to welcome the customer and ask for their name and to make a pin
    private void welcome() {
        System.out.println("Welcome");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter a PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
        customer = new Customer(name, pin);
        System.out.println();
        System.out.println("Welcome, " + customer.getName() + "!");
        System.out.println("You are now a Customer!");
    }

    // assigns savings and checking account for the customer
    private void assignAccounts() {
        savings = new Account("savings", 0, customer);
        checking = new Account("checking", 0, customer);
        System.out.println("You have been assigned two accounts: a savings account and a checking account");
        System.out.println();
    }

    // checks if pin is correct and informs customer if pin is incorrect
    // allows customer to retype pin until correct
    private void checkPin() {
        System.out.print("Please enter your PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
        while (customer.getPin() != pin) {
            System.out.println(ConsoleUtility.RED + "Incorrect PIN" + ConsoleUtility.RESET);
            System.out.print("Please enter your PIN: ");
            pin = scan.nextInt();
            scan.nextLine();
        }
    }

    // displays a menu of options for customer.
    // customer can choose an action from the menu.
    // Customer can continue using the ATM as long as they want.
    // if option is to exit or if customer doesn't want to use the ATM anymore,
    // this method will end and a thank-you message will display
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
                System.out.println(ConsoleUtility.RED + "Invalid option" + ConsoleUtility.RESET);
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

    // method that process options that the customer choices from the main menu
    private void processOption(int option) {
        // if option were 1 or 2, the customer will be asked which account they want to use
        if (option == 1 || option == 2) {
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            System.out.print("Choose an account: ");
            int account = scan.nextInt();
            System.out.println();
            while (account != 1 && account != 2) {
                System.out.println(ConsoleUtility.RED + "Invalid option" + ConsoleUtility.RESET);
                System.out.print("Choose an account: ");
                account = scan.nextInt();
                System.out.println();
            }
            scan.nextLine();
            // if the customer wishes to withdraw,
            // customer is told that ATM only gives out 5 and 20 dollar bills and must choose a valid amount of money to withdraw
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
                // if the customer chose to use their savings account, this part of the program withdraws money from the savings account
                if (account == 1) {
                    String summary = "Withdrew " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " from savings";
                    // if the account doesn't have enough money to preform the action,
                    // the customer is informed and the transaction is unsuccessful and a transaction ID is given
                    if (savings.getCurrentBalance() < amount) {
                        System.out.println("Insufficient Funds!");
                        System.out.println("You do not have enough money in your account to withdraw that amount");
                        System.out.println("Withdrawal was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                        System.out.println();
                    } else {
                        // if the account does have enough money,
                        // customer is asked how many 20 dollar bills they wish to receive.
                        // customer must enter a valid number of 20 dollar bills
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
                        // withdrawal will be a success and a transaction ID is given
                        System.out.println("Withdrawal was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                        System.out.println(summary);
                        System.out.println(savings.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                    }
                } else {
                    // if the customer chose to use their checking account, this part of the program withdraws money from the checking account
                    String summary = "Withdrew " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " from checking";
                    // if the account doesn't have enough money to preform the action,
                    // the customer is informed and the transaction is unsuccessful and a transaction ID is given
                    if (checking.getCurrentBalance() < amount) {
                        System.out.println("Insufficient Funds!");
                        System.out.println("You do not have enough money in your account to withdraw that amount");
                        System.out.println("Withdrawal was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                        System.out.println();
                    } else {
                        // if the account does have enough money,
                        // customer is asked how many 20 dollar bills they wish to receive.
                        // customer must enter a valid number of 20 dollar bills
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
                        System.out.println(checking.numberOfBills(amount, twenties));
                        checking.withdraw(amount);
                        // withdrawal will be a success and a transaction ID is given
                        System.out.println("Withdrawal was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                        System.out.println(summary);
                        System.out.println(checking.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                    }
                }
            } else {
                // if the customer wishes to deposit, they must enter the amount they wish to deposit
                System.out.print("Enter the amount you want to deposit: ");
                double amount = scan.nextDouble();
                scan.nextLine();
                System.out.println();
                // if the customer chose to use their savings account, their deposit will go there
                if (account == 1) {
                    String summary = "";
                    if (amount == (int) amount) {
                        summary = "Deposited " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " into savings";
                    } else {
                        summary = "Deposited " + ConsoleUtility.YELLOW + "$" + amount + ConsoleUtility.RESET + " into savings";
                    }
                    if (amount > 0) {
                        // if the deposit was successful, the customer is informed and a transaction ID is given
                        savings.deposit(amount);
                        System.out.println("Deposit was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                        System.out.println(summary);
                        System.out.println(savings.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                    } else {
                        // if the deposit was unsuccessful, the customer is informed and a transaction ID is given
                        System.out.println("Deposit must be positive");
                        System.out.println("Deposit was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                    }
                } else {
                    // if the customer chose to use their checking account, their deposit will go there
                    String summary  = "";
                    if (amount == (int) amount) {
                        summary = "Deposited " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " into checking";
                    } else {
                        summary = "Deposited " + ConsoleUtility.YELLOW + "$" + amount + ConsoleUtility.RESET + " into checking";
                    }
                    if (amount > 0) {
                        // if the deposit was successful, the customer is informed and a transaction ID is given
                        checking.deposit(amount);
                        System.out.println("Deposit was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                        System.out.println(summary);
                        System.out.println(checking.balanceMessage());
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                    } else {
                        // if the deposit was unsuccessful, the customer is informed and a transaction ID is given
                        System.out.println("Deposit must be positive");
                        System.out.println("Deposit was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                        System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                    }
                }
            }
        }
        // if customer wishes to transfer money between accounts
        if (option == 3) {
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");
            // customer chooses which account to transfer from, account must exist
            System.out.print("Which account is the transfer FROM: ");
            int account = scan.nextInt();
            while (account != 1 && account != 2) {
                System.out.println(ConsoleUtility.RED + "Invalid option" + ConsoleUtility.RESET);
                System.out.print("Which account is the transfer FROM: ");
                account = scan.nextInt();
            }
            System.out.print("How much are you going to transfer: ");
            double amount = scan.nextDouble();
            scan.nextLine();
            System.out.println();
            // this part of the program runs if the customer chose to transfer from the savings account
            if (account == 1) {
                String summary = "";
                if (amount == (int) amount) {
                    summary = "Transferred " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " from " + savings.getAccountName() + " to " + checking.getAccountName();
                } else {
                    summary = "Transferred " + ConsoleUtility.YELLOW + "$" + amount + ConsoleUtility.RESET + " from " + savings.getAccountName() + " to " + checking.getAccountName();
                }
                if (savings.getCurrentBalance() >= amount) {
                    savings.transfer(amount, checking);
                    // if transfer was successful, customer is informed and transaction ID is given
                    System.out.println("Transfer was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                    System.out.println(summary);
                    System.out.println(savings.balanceMessage());
                    System.out.println(checking.balanceMessage());
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", savings));
                } else {
                    // if transfer was unsuccessful, customer is informed and transaction ID is givem
                    System.out.println("Transfer amount must NOT exceed current balance on account");
                    System.out.println("Transfer was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", savings));
                }
            } else {
                // runs if the customer chose to transfer from the checking account
                String summary = "";
                if (amount == (int) amount) {
                    summary = "Transferred " + ConsoleUtility.YELLOW + "$" + amount + "0" + ConsoleUtility.RESET + " from " + checking.getAccountName() + " to " + savings.getAccountName();
                } else {
                    summary = "Transferred " + ConsoleUtility.YELLOW + "$" + amount + ConsoleUtility.RESET + " from " + checking.getAccountName() + " to " + savings.getAccountName();
                }
                if (checking.getCurrentBalance() >= amount) {
                    checking.transfer(amount, savings);
                    // if transfer was successful, customer is informed and transaction ID is given
                    System.out.println("Transfer was " + ConsoleUtility.GREEN + "successful" + ConsoleUtility.RESET);
                    System.out.println(summary);
                    System.out.println(savings.balanceMessage());
                    System.out.println(checking.balanceMessage());
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Successful", checking));
                } else {
                    // if transfer was unsuccessful, customer is informed and transaction ID is givem
                    System.out.println("Transfer amount must NOT exceed current balance on account");
                    System.out.println("Transfer was " + ConsoleUtility.RED + "unsuccessful" + ConsoleUtility.RESET);
                    System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "A", "Unsuccessful", checking));
                }
            }
        }
        // if customer wishes to view the account balances, this code runs.
        // allows customer to view account balances and a transaction ID is given
        if (option == 4) {
            System.out.println(savings.balanceMessage());
            System.out.println(checking.balanceMessage());
            String summary = "Viewed account balances";
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
        // if customer wishes to view transaction history, this code runs.
        // allows customer to view transaction history and a transaction ID is given
        if (option == 5) {
            System.out.println(transactionHistory.getTransactionHistory());
            String summary = "Viewed Transaction History";
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
        // if customer wishes to change their pin, this code runs.
        // allows the customer to change their pin and a transaction ID is given
        if (option == 6) {
            System.out.print("Enter your new PIN: ");
            int newPIN = scan.nextInt();
            scan.nextLine();
            customer.setPin(newPIN);
            String summary = "Changed PIN";
            System.out.println(ConsoleUtility.PURPLE + summary + ConsoleUtility.RESET);
            System.out.println("Transaction ID: " + transactionHistory.addTransaction(summary, "S", "Successful", null));
        }
    }

}