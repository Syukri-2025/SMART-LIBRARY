import java.util.Scanner;
import java.util.InputMismatchException;

public class SmartLibrary implements LibraryADT {
    // create instances of the tree catalog and the history stack
    private final BookBST catalogue = new BookBST();
    private final BorrowStack history = new BorrowStack();

    @Override
    public void addBook(int isbn, String title, String author, int stock) {
        // insert the book into the bst database tree
        catalogue.insert(isbn, title, author, stock);
        System.out.println("✅ Database Updated: Inventory values synchronized successfully.");
    }

    @Override
    public void searchBook(int isbn) {
        // look for the book in the tree catalog
        Book b = catalogue.search(isbn);
        // if found, show its information and current stock levels
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
        // find the book first before lending it out
        Book b = catalogue.search(isbn);
        if (b != null) {
            // make sure there is at least one copy left on the shelf
            if (b.availableStock > 0) {
                b.availableStock--; 
                
                // create a temporary snapshot record for the history stack
                Book transactionRecord = new Book(b.isbn, b.title, b.author, b.totalStock);
                transactionRecord.availableStock = b.availableStock;
                transactionRecord.daysKept = 0; 
                
                // push this snapshot record on top of the history stack
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
        // find the book in the catalog tree
        Book b = catalogue.search(isbn);
        if (b != null) {
            // make sure we are not returning more books than the total stock limit
            if (b.availableStock < b.totalStock) {
                b.availableStock++; 
                
                // calculate late fees if the book is kept over 7 days
                double fine = 0.0;
                if (daysKept > 7) { 
                    int overdueDays = daysKept - 7;
                    fine = overdueDays * 2.00; 
                }

                // create a snapshot record containing the calculated fine
                Book returnRecord = new Book(b.isbn, b.title, b.author, b.totalStock);
                returnRecord.daysKept = daysKept;
                returnRecord.fineAccumulated = fine;
                // push the return record onto the history stack
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
        // call the show method to print the complete history stack
        history.show();
    }

    // ==========================================================
    // FIXED MENU ENGINE CONTROLLER
    // ==========================================================
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        // keep running the application console menu loop continuously
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
            
            // fetch and validate that the input is a proper number
            int choice = getValidInteger(sc);

            // if user selects option 7, stop the loop and exit the system cleanly
            if (choice == 7) {
                System.out.println("👋 Terminating the program. Goodbye!");
                break; 
            }
            
            // process options 1 through 6 using the choice handler
            handleChoice(choice, sc);
        }
        sc.close(); // clean up the scanner memory resource
    }

    // private helper method to execute operations based on selected choice
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
                // print all catalog books alphabetically
                catalogue.printAlphabetical();
                break;

            case 6:
                // open up and print the history logs stack
                viewLatestHistory();
                break;

            default:
                System.out.println("⚠️ Invalid option selection. Please select an option between 1 and 7.");
        }
    }

    // private helper method to shield the program from breaking on text inputs
    private int getValidInteger(Scanner sc) {
        while (true) {
            try {
                int value = sc.nextInt();
                sc.nextLine(); // wipe the newline out of scanner memory buffer
                return value;
            } catch (InputMismatchException e) {
                // handle non-integer text entries gracefully without breaking the loop
                System.out.print("⚠️ Numeric format mismatch! Please re-type an integer value: ");
                sc.nextLine(); // clear out the corrupted text data from the buffer
            }
        }
    }
}