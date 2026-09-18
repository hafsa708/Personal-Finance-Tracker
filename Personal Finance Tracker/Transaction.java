public abstract class Transaction {
    private int id;
    private double amount;
    private String category;
    private String date;

    public Transaction(int id, double amount, String category, String date) {
        this.id = id;
        setAmount(amount); // Uses the validation setter safely
        this.category = category;
        this.date = date;
    }

    // Abstract method for True Polymorphism
    public abstract void printDetails();

    // File-saving helper method
    public abstract String toFileString();

    // Getters and Setters with Encapsulation Validation
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) {
        // Encapsulation validation: preventing negative financial entries
        if (amount < 0) {
            this.amount = 0;
        } else {
            this.amount = amount;
        }
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}