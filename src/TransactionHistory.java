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
                transactions += summary + ", " + type + zeroString + aTransactionsNum + ", " + "Status: " + status + "\n";
            } else {
                transactions += summary + ", " + type + zeroString + aTransactionsNum + ", " + "Status: " + status + ", " + account.getAccountName() + " account balance: $" + account.getCurrentBalance() + "\n";
            }
            return type + zeroString + aTransactionsNum;
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
                transactions += summary + ", " + type + zeroString + sTransactionsNum + ", " + "Status: " + status + "\n";
            } else {
                transactions += summary + ", " + type + zeroString + sTransactionsNum + ", " + "Status: " + status + ", " + account.getAccountName() + " account balance: $" + account.getCurrentBalance() + "\n";
            }
            return type + zeroString + sTransactionsNum;
        }
    }

}