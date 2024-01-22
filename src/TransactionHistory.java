public class TransactionHistory {

    private static int aTransactionsNum = -1;
    private static int sTransactionsNum = -1;
    private String transactions;

    public TransactionHistory() {
        transactions = "";
    }

    public String getTransactionHistory() {
        return transactions;
    }

    public String addTransaction(String summary, String type, String status, Account account) {
        String zeroString = "";
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