public class Expense extends Transaction {
    private String paymentMethod;

    public Expense(int id, double amount, String category, String date, String paymentMethod) {
        super(id, amount, category, date);
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public void printDetails() {
        System.out.printf("💸 [EXPENSE] ID: %d | Amount: %.2f ETB | Category: %s | Date: %s | Method: %s\n",
                getId(), getAmount(), getCategory(), getDate(), paymentMethod);
    }

    @Override
    public String toFileString() {
        return String.format("EXPENSE,%d,%.2f,%s,%s,%s", 
                getId(), getAmount(), getCategory(), getDate(), paymentMethod);
    }
}