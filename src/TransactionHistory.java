// Class that represents the Transaction History

public class TransactionHistory {

    // static variables

    // the number that goes at the end of the transaction ID for Account Transactions
    private static int aTransactionsNum = -1;
    // the number that goes at the end of the transaction ID for Security Transactions
    private static int sTransactionsNum = -1;

    //instance variables
    private String transactions;

    // constructor
    public TransactionHistory() {
        transactions = "";
    }

    // method to get transaction history
    public String getTransactionHistory() {
        return transactions;
    }

    // method to add transaction to transaction history and return a transaction ID
    public String addTransaction(String summary, String type, String status, Account account) {
        String zeroString = "";
        // if the transaction was an account transaction
        if (type.equals("A")) {
            aTransactionsNum++;
            if (aTransactionsNum > 999) {
                zeroString = "";
            } else if (aTransactionsNum > 99) {
                zeroString = "0";
            } else if (aTransactionsNum > 9) {
                zeroString = "00";
            } else {
                zeroString = "000";
            }
            if (account == null) {
                transactions += summary + ", " + ConsoleUtility.CYAN + type + zeroString + aTransactionsNum + ConsoleUtility.RESET + ", " + "Status: " + status + "\n";
            } else {
                transactions += summary + ", " + ConsoleUtility.CYAN + type + zeroString + aTransactionsNum + ConsoleUtility.RESET + ", " + "Status: " + status + ", " + account.getAccountName() + " account balance: $" + account.getCurrentBalance() + "\n";
            }
            return ConsoleUtility.CYAN + type + zeroString + aTransactionsNum + ConsoleUtility.RESET;
        } else {
            // if the transaction was a security transaction
            sTransactionsNum++;
            if (sTransactionsNum > 999) {
                zeroString = "";
            } else if (sTransactionsNum > 99) {
                zeroString = "0";
            } else if (sTransactionsNum > 9) {
                zeroString = "00";
            } else {
                zeroString = "000";
            }
            if (account == null) {
                transactions += summary + ", " + ConsoleUtility.CYAN + type + zeroString + sTransactionsNum + ConsoleUtility.RESET + ", " + "Status: " + status + "\n";
            } else {
                transactions += summary + ", " + ConsoleUtility.CYAN + type + zeroString + sTransactionsNum + ConsoleUtility.RESET + ", " + "Status: " + status + ", " + account.getAccountName() + " account balance: $" + account.getCurrentBalance() + "\n";
            }
            return ConsoleUtility.CYAN + type + zeroString + sTransactionsNum + ConsoleUtility.RESET;
        }
    }

}