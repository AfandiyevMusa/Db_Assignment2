import java.sql.Connection;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        DbFunctions db=new DbFunctions();

        Connection conn=db.connect_to_db();

        // CREATING BOOK

//         First version
//        Book book = new Book();
//        book.setTitle("Sample Title");
//        book.setGenre("Sample Genre");
//        book.setPrice(19.99);
//        book.setStockQuantity(100);

        // Second version
//        Book newBook = new Book("Sample Title", "Sample Genre", 19.99, 100);

//        db.insertBook(book);


        // CREATING AUTHOR
        //first version
//        Author newAuthor = new Author();
//        newAuthor.setAuthorName("John Doe");
//        newAuthor.setBirthDate(LocalDate.parse("1990-01-01")); // Adjust the date format based on your input
//        newAuthor.setCountry("United States");

        //second version
//        Author newAuthor = new Author("John Doe", LocalDate.parse("1990-01-01"), "United States");
//        db.insertAuthor(newAuthor);


        // CREATING CUSTOMER
//        Customer newCustomer = new Customer("John", "Doe", "john.doe@gmail.com", "123-456-7890");
//        db.insertCustomer(newCustomer);


//        db.retrieveAllAuthors();
//        db.retrieveAllCustomers();
//        db.retrieveAllBooksWithAuthorsAndOrders();
//    db.retrieveAllBooks();

//        db.updateBook(10, "Hello", "Classic", 20, 100, 2); // which details all or not
//        db.updateAuthor(1, "Musa Afandiyev", "2003-09-01", "Azerbaijan");
//        db.updateCustomer(3, "Bobb", "Anderson", "bob.anderson@gmail.com", "609-803-8888");

//        db.deleteBook(12);
//        db.deleteAuthor(7);
//        db.deleteCustomer(4);

//        db.placeOrder(3, 1, 5);

//        db.displayTablesInfo();  ///add primary keys and foreign keys info
//        db.displayColumnsInfo(conn, "books");
//        db.displayKeysInfo(conn, "books");

    }
}