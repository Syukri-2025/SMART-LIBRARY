 public class Book {
    public int isbn;
    public String title, author;
    public int totalStock;
    public int availableStock;
    public int daysKept; 
    public double fineAccumulated; 
    public Book left, right;

    public Book(int isbn, String title, String author, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalStock = stock;
        this.availableStock = stock;
        this.daysKept = 0;
        this.fineAccumulated = 0.0;
        this.left = null;
        this.right = null;
    }
}