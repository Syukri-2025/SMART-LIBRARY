public interface LibraryADT {
    void addBook(int isbn, String title, String author, int stock);
    void searchBook(int isbn);
    void borrowBook(int isbn);
    void returnBook(int isbn, int daysKept);
    void viewLatestHistory();
}