import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookBST {
    // the root node at the very top of the tree
    private Book root;

    // public method to add a book to the tree
    public void insert(int isbn, String t, String a, int stock) {
        root = ins(root, isbn, t, a, stock);
    }

    // helper method that uses recursion to find where to put the book
    private Book ins(Book r, int i, String t, String a, int stock) {
        // if the spot is empty, create and put the new book here
        if (r == null) return new Book(i, t, a, stock);
        
        // if the new isbn is smaller, go down the left branch
        if (i < r.isbn) {
            r.left = ins(r.left, i, t, a, stock);
        // if the new isbn is bigger, go down the right branch
        } else if (i > r.isbn) {
            r.right = ins(r.right, i, t, a, stock);
        // if the isbn already exists, just update the stock quantity
        } else {
            r.totalStock += stock;
            r.availableStock += stock;
            System.out.println("🔄 Existing ISBN found. Inventory restocked.");
        }
        return r; // return the current node pointer
    }

    // public method to look up a book using its isbn number
    public Book search(int i) {
        return sea(root, i);
    }

    // helper method that uses recursion to search through the branches
    private Book sea(Book r, int i) {
        // stop if the book is not found or if we find a match
        if (r == null || r.isbn == i) return r;
        
        // if target isbn is smaller, search left. otherwise, search right.
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }

    // public method to copy, sort, and show all books alphabetically
    public void printAlphabetical() {
        // create a temporary list to hold the books
        List<Book> bookList = new ArrayList<>();
        
        // use traversal to gather all books from the tree into the list
        collectNodes(root, bookList);

        // stop here if the tree has no books
        if (bookList.isEmpty()) {
            System.out.println("📚 The catalogue is completely empty.");
            return;
        }

        // sort the list by book title (ignores capital letters)
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.title.compareToIgnoreCase(b2.title);
            }
        });

        // print the sorted list to the console
        System.out.println("\n--- 🔤 Books in Alphabetical Order ---");
        for (Book b : bookList) {
            System.out.println("• \"" + b.title + "\" by " + b.author + 
                               " [ISBN: " + b.isbn + "] | Stock Available: " + b.availableStock + "/" + b.totalStock);
        }
    }

    // helper method to copy tree nodes into a list (left, node, right order)
    private void collectNodes(Book node, List<Book> list) {
        if (node == null) return; // stop if the spot is empty
        
        collectNodes(node.left, list);  // visit left branch
        list.add(node);                 // add current book to list
        collectNodes(node.right, list); // visit right branch
    }
}