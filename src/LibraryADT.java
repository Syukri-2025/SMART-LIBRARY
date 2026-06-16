/**
 * An interface that describes the operations of a Smart Library system.
 * This acts as an Abstract Data Type (ADT) and defines the available
 * operations without exposing internal implementation details.
 * This interface demonstrates:
 * - Abstraction
 * - Encapsulation
 * - Information Hiding
 */
interface LibraryADT {
 
    /**
     * Adds a new book or restocks an existing one in the library catalogue.
     * @param isbn the unique ISBN of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param stock the number of copies added or available in stock
     * @return true if the book is successfully added or restocked, false otherwise
     */
    void addBook(int isbn, String title, String author, int stock);
    
     /**
     * Searches for a book in the catalogue using its ISBN.
     * @param isbn the ISBN of the book to search for
     * @return true if the book is found, false otherwise
     */
    void searchBook(int isbn);
    
    /**
     * Borrows a book from the catalogue and updates available stock.
     * @param isbn the ISBN of the book to borrow
     * @return true if the book is successfully borrowed, false otherwise
     */
    void borrowBook(int isbn);
    
    /**
     * Returns a previously borrowed book and calculates any late fines.
     * @param isbn the ISBN of the book being returned
     * @param daysKept number of days the book was kept before returning
     * @return true if the return is successful, false otherwise
     */
    void returnBook(int isbn, int daysKept);
    
    /**
     * Displays the borrowing history in LIFO order (newest to oldest).
     * @return true if history exists and is displayed, false if history is empty
     */
    void viewLatestHistory();
}