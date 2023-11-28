import models.OrderRequest;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        DbFunctions db=new DbFunctions();

        Connection conn=db.connect_to_db();

        // CREATING BOOK
//        Book newBook = new Book("Sample Title", "Sample Genre", 19.99, 100);
//        db.insertBook(book);

        // CREATING AUTHOR
//        Author newAuthor = new Author("John Doe", LocalDate.parse("1990-01-01"), "United States");
//        db.insertAuthor(newAuthor);

        // CREATING CUSTOMER
//        Customer newCustomer = new Customer("John", "Doe", "john.doe@gmail.com", "123-456-7890");
//        db.insertCustomer(newCustomer);


//        List<models.Book> books = db.retrieveAllBooks();
//        for (models.Book book : books) {
//            System.out.println(book);
//        }

//        List<models.Author> authors = db.retrieveAllAuthors();
//        for (models.Author author : authors) {
//            System.out.println(author);
//        }

//        List<models.Customer> customers = db.retrieveAllCustomers();
//        for (models.Customer customer : customers) {
//            System.out.println(customer);
//        }

//        List<models.Book> booksWithAuthorsAndOrders = db.retrieveAllBooksWithAuthorsAndOrders();
//        for (models.Book book : booksWithAuthorsAndOrders) {
//            System.out.println(book);
//        }


//        db.updateBook(10, " ", "Fantastic", 19.99, 24, 1);
//        db.updateAuthor(1, " ", "2003-09-01", "Azerbaijan");
//        db.updateCustomer(3, " ", "Anderson", "bob.anderson@gmail.com", "609-803-8888");

//        db.deleteBook(12);
//        db.deleteAuthor(7);
//        db.deleteCustomer(4);


        List<OrderRequest> orderRequests = new ArrayList<>();
        orderRequests.add(new OrderRequest(2,
                Arrays.asList(2, 1),
                Arrays.asList(3, 5)));

        for (OrderRequest orderRequest : orderRequests) {
            db.placeOrder(orderRequest);
        }


//        db.displayTablesInfo();  ///add primary keys and foreign keys info
//        db.displayColumnsInfo(conn, "books");
//        db.displayKeysInfo(conn, "books");

    }
}