import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FinanceManager {
    private ArrayList<Transaction> transactions;
    private final String FILE_PATH = "transactions.txt";

    public FinanceManager() {
        this.transactions = new ArrayList<>();
        loadTransactionsFromFile(); // Optional persistence achieved on boot!
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        System.out.println("✅ Transaction record added successfully!");
        saveTransactionsToFile(); 
    }

    public void viewAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("ℹ️ No transaction history found.");
            return;
        }
        System.out.println("\n--- 📊 CURRENT FINANCIAL LEDGER ---");
        // TRUE POLYMORPHISM: The exact print variant is decided at runtime!
        for (Transaction t : transactions) {
            t.printDetails(); 
        }
    }

    public void updateTransactionAmount(int id, double newAmount) {
        Transaction found = findById(id);
        if (found != null) {
            found.setAmount(newAmount); // Encapsulated setter validation runs automatically
            System.out.println("✅ Transaction amount modified successfully!");
            saveTransactionsToFile();
        } else {
            System.out.println("❌ Error: Transaction ID not found.");
        }
    }

    public void deleteTransaction(int id) {
        Transaction found = findById(id);
        if (found != null) {
            transactions.remove(found);
            System.out.println("🗑️ Transaction record purged safely.");
            saveTransactionsToFile();
        } else {
            System.out.println("❌ Error: Transaction ID not found.");
        }
    }

    private Transaction findById(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // Helper method to automatically track the highest ID so counters don't reset
    public int getMaxId() {
        int max = 0;
        for (Transaction t : transactions) {
            if (t.getId() > max) {
                max = t.getId();
            }
        }
        return max;
    }

    // OPTIONAL PERSISTENCE FEATURE: File Writer Implementation
    private void saveTransactionsToFile() {
        try (PrintWriter writer = new PrintWriter(new File(FILE_PATH))) {
            for (Transaction t : transactions) {
                writer.println(t.toFileString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ Warning: Could not save transaction states to file.");
        }
    }

    // OPTIONAL PERSISTENCE FEATURE: File Parsing & Load Implementation
    private void loadTransactionsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] p = line.split(",");
                if (p.length < 5) continue;

                String type = p[0];
                int id = Integer.parseInt(p[1]);
                double amount = Double.parseDouble(p[2]);
                String category = p[3];
                String date = p[4];
                String specificField = p[5];

                if (type.equals("INCOME")) {
                    transactions.add(new Income(id, amount, category, date, specificField));
                } else if (type.equals("EXPENSE")) {
                    transactions.add(new Expense(id, amount, category, date, specificField));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Notice: Initial transaction records could not be loaded cleanly.");
        }
    }
}

