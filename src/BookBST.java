class BookBST {
    private Book root;
    public void insert(String title, String author, int isbn) {
        root = insertRec(root, title, author, isbn);
    }
    private Book insertRec(Book root, String title , String author, int isbn){
        if(root==null){
            root=new Book(title, author, isbn );
            return root;
        }else if (isbn < root.isbn){
            root.left = insertRec(root.left, title, author, isbn);
        }else if (isbn > root.isbn){
            root.right = insertRec(root.right, title, author, isbn);
        }else{
            System.out.println("Duplicate ISBN not allowed: " + isbn);
        }
        return root;
    }

    public Book findBookByISBN(int targetIsbn) {
    // We start the search at the very top of the tree (the root node)
    return recursiveSearch(root, targetIsbn);
    }

    private Book recursiveSearch(Book currentNode, int targetIsbn) {
    
    // BASE CASE 1: We hit an empty spot. The book does not exist.
    if (currentNode == null) {
        return null; 
    }

    // BASE CASE 2: The current node's ISBN matches our target. We found it!
    if (currentNode.isbn == targetIsbn) {
        return currentNode; 
    }

    // RECURSIVE STEP: If we haven't found it, do we go left or right?
    if (targetIsbn < currentNode.isbn) {
        // The target is smaller, so we search down the left branch
        return recursiveSearch(currentNode.left, targetIsbn);
        
    } else {
        // The target is larger, so we search down the right branch
        return recursiveSearch(currentNode.right, targetIsbn);
    }
}
}
