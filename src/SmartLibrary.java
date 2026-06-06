import java.util.Scanner;
import java.util.InputMismatchException;

public class SmartLibrary implements LibraryADT {
    private final BookBST catalogue = new BookBST();
    private final BorrowStack history = new BorrowStack();

    @Override
    public void addBook(int isbn, String title, String author, int stock) {
        catalogue.insert(isbn, title, author, stock);
        System.out.println("✅ Database Updated: Inventory values synchronized successfully.");
    }

    @Override
    public void searchBook(int isbn) {
        Book b = catalogue.search(isbn);
        if (b != null) {
            System.out.println("🔍 Catalogue Item Found:");
            System.out.println("   Title: \"" + b.title + "\" | Author: " + b.author);
            System.out.println("   📈 Stock Levels: [" + b.availableStock + " / " + b.totalStock + " copies available]");
        } else {
            System.out.println("❌ Not Found: This ISBN does not exist in our systems.");
        }
    }

    @Override
    public void borrowBook(int isbn) {
        Book b = catalogue.search(isbn);
        if (b != null) {
            if (b.availableStock > 0) {
                b.availableStock--; 
                
                Book transactionRecord = new Book(b.isbn, b.title, b.author, b.totalStock);
                transactionRecord.availableStock = b.availableStock;
                transactionRecord.daysKept = 0; 
                
                history.push(transactionRecord); 
                System.out.println("📖 Transaction Approved: \"" + b.title + "\" checked out.");
            } else {
                System.out.println("❌ Stock Out: All copies of this title are currently loaned out.");
            }
        } else {
            System.out.println("❌ Error: Invalid system transaction lookup. ISBN not found.");
        }
    }

    @Override
    public void returnBook(int isbn, int daysKept) {
        Book b = catalogue.search(isbn);
        if (b != null) {
            if (b.availableStock < b.totalStock) {
                b.availableStock++; 
                
                double fine = 0.0;
                if (daysKept > 7) { 
                    int overdueDays = daysKept - 7;
                    fine = overdueDays * 2.00; 
                }

                Book returnRecord = new Book(b.isbn, b.title, b.author, b.totalStock);
                returnRecord.daysKept = daysKept;
                returnRecord.fineAccumulated = fine;
                history.push(returnRecord);

                System.out.println("✅ Process Return: Inventory restored to shelves.");
                if (fine > 0) {
                    System.out.println("⚠️ OVERDUE ACTION ALERT: Book kept for " + daysKept + " days.");
                    System.out.println("   💵 Balance Due / Fine Charged: $" + String.format("%.2f", fine));
                } else {
                    System.out.println("   🎉 Completed on time! Account status clear.");
                }
            } else {
                System.out.println("⚠️ Database Conflict: All copies are already checked back in.");
            }
        } else {
            System.out.println("❌ Error: Invalid returning registration index.");
        }
    }

    @Override
    public void viewLatestHistory() {
        history.show();
    }

    // ==========================================================
    // FIXED MENU ENGINE CONTROLLER
    // ==========================================================
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- SmartLibrary ERP Menu ---");
            System.out.println("1. Add / Restock Book Inventory");
            System.out.println("2. Search Item & Check Stock Levels");
            System.out.println("3. Checkout Book (Borrow)");
            System.out.println("4. Checkin Book & Assess Fines (Return)");
            System.out.println("5. View All Books in Alphabetical Order 🔤");
            System.out.println("6. View Full Transactions & Fine Logs");
            System.out.println("7. Exit System Application");
            System.out.print("Choice: ");
            System.out.flush(); 
            
            int choice = getValidInteger(sc);

            // FIXED LOOP CONTROL: Intercept option 7 directly here to break the while loop cleanly
            if (choice == 7) {
                System.out.println("👋 Terminating the program. Goodbye!");
                break; 
            }
            
            handleChoice(choice, sc);
        }
        sc.close();
    }

    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                System.out.print("Enter ISBN: ");
                int isbn = getValidInteger(sc);
                System.out.print("Enter Title: ");
                String title = sc.nextLine(); 
                System.out.print("Enter Author: ");
                String author = sc.nextLine();
                System.out.print("Enter Stock Quantity: ");
                int stock = getValidInteger(sc);
                addBook(isbn, title, author, stock);
                break;

            case 2:
                System.out.print("Enter ISBN to inspect: ");
                searchBook(getValidInteger(sc));
                break;

            case 3:
                System.out.print("Enter ISBN to borrow: ");
                borrowBook(getValidInteger(sc));
                break;

            case 4:
                System.out.print("Enter ISBN to return: ");
                int retIsbn = getValidInteger(sc);
                System.out.print("Enter total days customer kept the item: ");
                int days = getValidInteger(sc);
                returnBook(retIsbn, days);
                break;

            case 5:
                catalogue.printAlphabetical();
                break;

            case 6:
                viewLatestHistory();
                break;

            default:
                System.out.println("⚠️ Invalid option selection. Please select an option between 1 and 7.");
        }
    }

    private int getValidInteger(Scanner sc) {
        while (true) {
            try {
                int value = sc.nextInt();
                sc.nextLine(); 
                return value;
            } catch (InputMismatchException e) {
                System.out.print("⚠️ Numeric format mismatch! Please re-type an integer value: ");
                sc.nextLine(); 
            }
        }
    }
}