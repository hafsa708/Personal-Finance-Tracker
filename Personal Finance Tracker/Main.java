import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String savedUser = "";
        String savedPass = "";

        // 1. TEXT FILE I/O: Load credentials from file
        try {
            File file = new File("credentials.txt");
            Scanner fileReader = new Scanner(file);
            if (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                savedUser = parts[0];
                savedPass = parts[1];
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Critical Error: credentials.txt file is missing!");
            return;
        }

        // Create the encapsulated User object from file data
        User secureUser = new User(savedUser, savedPass);

        // 2. OPEN INPUT STREAM
        Scanner scanner = new Scanner(System.in);

        // 3. LOGIN SYSTEM WITH EXCEPTION HANDLING
        System.out.println("SECURITY VERIFICATION REQUIRED");
        System.out.print("Enter Username: ");
        String inputUsername = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String inputPassword = scanner.nextLine();
        
        try {
            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                throw new InvalidCredentialsException("Input fields cannot be empty.");
            }
            
            // Validate using encapsulated getters
            if (inputUsername.equals(secureUser.getUsername()) && inputPassword.equals(secureUser.getPassword())) {
                System.out.println("\nAccess Granted. Welcome back!");
            } else {
                throw new InvalidCredentialsException("Invalid username or password.");
            }
        } catch (InvalidCredentialsException e) {
            System.out.println("\nAccess Denied: " + e.getMessage());
            scanner.close();
            return; 
        }
        
        // 4. ENGINE INITIALIZATION
        FinanceManager manager = new FinanceManager();
        int mainChoice = 0;
        
        // Automatically sync the runtime ID counter with our persistent database text file
        int idCounter = manager.getMaxId() + 1; 

        // 5. MANDATORY MAIN MENU NAVIGATION LAYER
        while (mainChoice != 4) {
            System.out.println("\n=================================");
            System.out.println("           MAIN MENU             ");
            System.out.println("=================================");
            System.out.println("1. Home");
            System.out.println("2. About");
            System.out.println("3. Perform Actions (CRUD System)");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");
        
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
                continue;
            }
            mainChoice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (mainChoice) {
                case 1: // Home
                    System.out.println("\n[HOME] Welcome to the Personal Finance Tracker Dashboard.");
                    break;

                case 2: // About
                    System.out.println("Title: Personal Finance Tracker");
                    System.out.println("GROUP MEMBERS:    ID");
                    System.out.println("1.  Hafsa Omer: 1311/17");      
                    System.out.println("2.  Kertina Abera: 1658/17");     
                    System.out.println("3.  Heaven Chala: 1413/17");
                    System.out.println("4.  Ferkiya Redwan: 1131/17");
                    System.out.println("5.  Frehiwot Ewnetu: 1224/17");
                    break;

                case 3: // Perform Actions (The original CRUD Menu inside a Submenu)
                    int crudChoice = 0;
                    while (crudChoice != 6) {
                        System.out.println("\n---  TRANSACTION MANAGEMENT (CRUD) ---");
                        System.out.println("1. Add Income");
                        System.out.println("2. Add Expense");
                        System.out.println("3. View All Transactions");
                        System.out.println("4. Update Transaction Amount");
                        System.out.println("5. Delete a Transaction");
                        System.out.println("6. Back to Main Menu");
                        System.out.print("Enter action (1-6): ");
                        
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input.");
                            scanner.nextLine();
                            continue;
                        }
                        crudChoice = scanner.nextInt();
                        scanner.nextLine(); // Clear buffer

                        switch (crudChoice) {
                            case 1:
                                System.out.print("Enter Amount (ETB): ");
                                double incAmount = scanner.nextDouble(); scanner.nextLine(); 
                                System.out.print("Enter Category: ");
                                String incCategory = scanner.nextLine();
                                System.out.print("Enter Date (YYYY-MM-DD): ");
                                String incDate = scanner.nextLine();
                                System.out.print("Enter Income Source: ");
                                String incomeSource = scanner.nextLine();

                                manager.addTransaction(new Income(idCounter++, incAmount, incCategory, incDate, incomeSource));
                                break;

                            case 2:
                                System.out.print("Enter Amount (ETB): ");
                                double expAmount = scanner.nextDouble(); scanner.nextLine();
                                System.out.print("Enter Category: ");
                                String expCategory = scanner.nextLine();
                                System.out.print("Enter Date (YYYY-MM-DD): ");
                                String expDate = scanner.nextLine();
                                System.out.print("Enter Payment Method: ");
                                String paymentMethod = scanner.nextLine();

                                manager.addTransaction(new Expense(idCounter++, expAmount, expCategory, expDate, paymentMethod));
                                break;

                            case 3:
                                manager.viewAllTransactions();
                                break;

                            case 4:
                                System.out.print("Enter Transaction ID to update: ");
                                int updateId = scanner.nextInt();
                                System.out.print("Enter new Amount (ETB): ");
                                double newAmount = scanner.nextDouble(); scanner.nextLine(); 
                                manager.updateTransactionAmount(updateId, newAmount);
                                break;

                            case 5:
                                System.out.print("Enter Transaction ID to delete: ");
                                int deleteId = scanner.nextInt(); scanner.nextLine(); 
                                manager.deleteTransaction(deleteId);
                                break;

                            case 6:
                                System.out.println("Returning to Main Menu...");
                                break;

                            default:
                                System.out.println("Invalid choice. Select 1-6.");
                        }
                    }
                    break;

                case 4: // Exit
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please select between 1 and 4.");
            }
        }
        scanner.close();
    }
}