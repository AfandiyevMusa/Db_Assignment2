import methods.*;
import models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        DbFunctions db = new DbFunctions();
        MetaData metaData = new MetaData();
        TransactionProcess transactionProcess = new TransactionProcess();
        BookOperations bookOperations = new BookOperations();
        AuthorOperations authorOperations = new AuthorOperations();
        CustomerOperations customerOperations = new CustomerOperations();
        OrderOperations orderOperations = new OrderOperations();

        Connection conn=db.connect_to_db();


    // BOOK CRUD

//        Book newBook = new Book("The Night Circus", "Fantasy", 39.99, 80, 3);
//        bookOperations.insertBook(newBook);

        // Show all books
//        List<models.Book> books = bookOperations.retrieveAllBooks();
//        for (models.Book book : books) {
//            System.out.println(book);
//        }

        // Show all orders with given bookId
//        System.out.println(bookOperations.retrieveBookInfoAndOrdersByBookId(2));

        // Updating Book
//        bookOperations.updateBook(10, " ", "Fantastic", 19.99, 24, 5);

        // Deleting Book
//        bookOperations.deleteBook(16);



    // AUTHOR CRUD
//        Author newAuthor = new Author("Musa Afandiyev", LocalDate.parse("2003-09-01"), "Azerbaijan");
//        authorOperations.insertAuthor(newAuthor);

        // Show all AUTHORS
//        List<models.Author> authors = authorOperations.retrieveAllAuthors();
//        for (models.Author author : authors) {
//            System.out.println(author);
//        }

        // Update Author
//        authorOperations.updateAuthor(9, "Rauf Rasulzada", "2003-09-01", "Azerbaijan");

        // Delete Author
//        authorOperations.deleteAuthor(3);




    //  CUSTOMER CRUD
//        Customer newCustomer = new Customer("Musa", "Afandiyev", "musa.afandiyev@gmail.com", "123-456-7890");
//        customerOperations.insertCustomer(newCustomer);

        // Show All Customers
//        List<models.Customer> customers = customerOperations.retrieveAllCustomers();
//        for (models.Customer customer : customers) {
//            System.out.println(customer);
//        }

        // Update Customer
//        customerOperations.updateCustomer(3, "Bob", "Anderson", "bob.anderson@gmail.com", "609-803-8888");
//        db.updateOrderDetails(20, Arrays.asList(1), Arrays.asList(2));

        // Delete Customer
//        customerOperations.deleteCustomer(5);


    // Order Operations
//        orderOperations.deleteOrder(19);

//        orderOperations.updateOrderDetails(34, Arrays.asList(2), Arrays.asList(2));

    // Transaction Operation

//        List<OrderRequest> orderRequests = new ArrayList<>();
//        orderRequests.add(new OrderRequest(3,
//                Arrays.asList(16, 1, 3),
//                Arrays.asList(12, 4, 5)));
//        for (OrderRequest orderRequest : orderRequests) {
//            transactionProcess.placeOrder(orderRequest);
//        }

       // Metadata
//        metaData.displayTablesInfoWithKeys();
//        metaData.displayColumnsInfo(conn, "books");

    }
}