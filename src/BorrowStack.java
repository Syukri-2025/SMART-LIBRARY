import java.util.Stack;

public class BorrowStack {
    // create the stack collection to store history logs
    private final Stack<Book> stack = new Stack<>();

    // public method to add a new transaction log on top of the stack
    public void push(Book b) {
        stack.push(b);
    }

    // public method to show all logs starting from the newest to oldest
    public void show() {
        // if the stack has no history logs, stop here
        if (stack.isEmpty()) {
            System.out.println("📜 System Log: Borrowing history tracking is empty.");
            return;
        }
        
        System.out.println("\n--- 📜 System History Logs (Most Recent First) ---");
        
        // loop backwards from the top of the stack to the bottom (lifo order)
        for (int i = stack.size() - 1; i >= 0; i--) {
            // grab the book log at index i
            Book b = stack.get(i);
            System.out.print("• [ISBN: " + b.isbn + "] \"" + b.title + "\"");
            
            // check if the book has been returned or is still outstanding
            if (b.daysKept > 0) {
                System.out.print(" | Status: RETURNED (Kept " + b.daysKept + " days)");
                
                // check if the customer had to pay a late fine
                if (b.fineAccumulated > 0) {
                    System.out.println(" ⚠️ FINE PAID: $" + String.format("%.2f", b.fineAccumulated));
                } else {
                    System.out.println(" ✅ Cleared On Time");
                }
            } else {
                System.out.println(" | Status: OUTSTANDING (Not Returned Yet)");
            }
        }
    }
}