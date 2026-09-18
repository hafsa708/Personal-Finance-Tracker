public class Income extends Transaction {
    private String incomeSource;

    public Income(int id, double amount, String category, String date, String incomeSource) {
        super(id, amount, category, date);
        this.incomeSource = incomeSource;
    }

    public String getIncomeSource() { return incomeSource; }
    public void setIncomeSource(String incomeSource) { this.incomeSource = incomeSource; }

    @Override
    public void printDetails() {
        System.out.printf("💰 [INCOME] ID: %d | Amount: %.2f ETB | Category: %s | Date: %s | Source: %s\n",
                getId(), getAmount(), getCategory(), getDate(), incomeSource);
    }

    @Override
    public String toFileString() {
        return String.format("INCOME,%d,%.2f,%s,%s,%s", 
                getId(), getAmount(), getCategory(), getDate(), incomeSource);
    }
}