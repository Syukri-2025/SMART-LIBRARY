public class Main {
   // the main execution entry point for the program
   public static void main(String[] args) {
       // print a loading message to the console
       System.out.println("🚀 Loading SmartLibrary Database ERP Suite...");
       
       // create a new instance of the smart library system
       SmartLibrary library = new SmartLibrary();
       
       // start the continuous loop for the menu interface
       library.runMenu();
   }
}