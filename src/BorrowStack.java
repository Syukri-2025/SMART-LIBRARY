import java.util.Stack;

public class BorrowStack {
    private final Stack<Book> stack = new Stack<>();

    public void push(Book b) {
        stack.push(b);
    }

    public void show() {
        if (stack.isEmpty()) {
            System.out.println("📜 System Log: Borrowing history tracking is empty.");
            return;
        }
        System.out.println("\n--- 📜 System History Logs (Most Recent First) ---");
        for (int i = stack.size() - 1; i >= 0; i--) {
            Book b = stack.get(i);
            System.out.print("• [ISBN: " + b.isbn + "] \"" + b.title + "\"");
            if (b.daysKept > 0) {
                System.out.print(" | Status: RETURNED (Kept " + b.daysKept + " days)");
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