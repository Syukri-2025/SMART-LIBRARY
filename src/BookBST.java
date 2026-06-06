import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookBST {
    private Book root;

    public void insert(int isbn, String t, String a, int stock) {
        root = ins(root, isbn, t, a, stock);
    }

    private Book ins(Book r, int i, String t, String a, int stock) {
        if (r == null) return new Book(i, t, a, stock);
        if (i < r.isbn) {
            r.left = ins(r.left, i, t, a, stock);
        } else if (i > r.isbn) {
            r.right = ins(r.right, i, t, a, stock);
        } else {
            r.totalStock += stock;
            r.availableStock += stock;
            System.out.println("🔄 Existing ISBN found. Inventory restocked.");
        }
        return r;
    }

    public Book search(int i) {
        return sea(root, i);
    }

    private Book sea(Book r, int i) {
        if (r == null || r.isbn == i) return r;
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }

    public void printAlphabetical() {
        List<Book> bookList = new ArrayList<>();
        collectNodes(root, bookList);

        if (bookList.isEmpty()) {
            System.out.println("📚 The catalogue is completely empty.");
            return;
        }

        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.title.compareToIgnoreCase(b2.title);
            }
        });

        System.out.println("\n--- 🔤 Books in Alphabetical Order ---");
        for (Book b : bookList) {
            System.out.println("• \"" + b.title + "\" by " + b.author + 
                               " [ISBN: " + b.isbn + "] | Stock Available: " + b.availableStock + "/" + b.totalStock);
        }
    }

    private void collectNodes(Book node, List<Book> list) {
        if (node == null) return;
        collectNodes(node.left, list);  
        list.add(node);                 
        collectNodes(node.right, list); 
    }
}