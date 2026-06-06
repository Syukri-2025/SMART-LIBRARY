interface LibraryADT {
    // method to add a new book or restock an existing one
    void addBook(int isbn, String title, String author, int stock);
    
    // method to search for a book and check its details using isbn
    void searchBook(int isbn);
    
    // method to process a book loan and update available stock
    void borrowBook(int isbn);
    
    // method to handle returning a book and calculating late fines
    void returnBook(int isbn, int daysKept);
    
    // method to display the borrowing history logs from newest to oldest
    void viewLatestHistory();
}