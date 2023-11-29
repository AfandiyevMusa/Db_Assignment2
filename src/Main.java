import methods.BookOperations;
import methods.MetaData;
import methods.TransactionProcess;
import models.Author;
import models.Book;
import models.Order;
import models.OrderRequest;

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

        Connection conn=db.connect_to_db();


        // Book CRUD

//        Book newBook = new Book("The Night Circus", "Fantasy", 39.99, 80, 3);
//        bookOperations.insertBook(newBook);

//        List<models.Book> books = bookOperations.retrieveAllBooks();
//        for (models.Book book : books) {
//            System.out.println(book);
//        }

        //It gives all books informations with authors and orders (there are duplicates in here)
//        List<models.Book> books = bookOperations.retrieveAllBooksWithAuthorsAndOrders();
//        for (models.Book book : books) {
//            System.out.println(book);
//        }

        //It gives all orders informations with given bookId.
        // It just return order informations about unique book.
//        List<Order> orders = bookOperations.retrieveOrdersByBookId(1);
//        for (Order order: orders){
//            System.out.println(order);
//        }

//        bookOperations.updateBook(10, " ", "Fantastic", 19.99, 24, 5);

//        bookOperations.deleteBook(10);



        // CREATING AUTHOR
//        Author newAuthor = new Author("John Doe", LocalDate.parse("1990-01-01"), "United States");
//        db.insertAuthor(newAuthor);

        // CREATING CUSTOMER
//        Customer newCustomer = new Customer("John", "Doe", "john.doe@gmail.com", "123-456-7890");
//        db.insertCustomer(newCustomer);




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

//        db.updateAuthor(1, " ", "2003-09-01", "Azerbaijan");
//        db.updateCustomer(3, " ", "Anderson", "bob.anderson@gmail.com", "609-803-8888");
//        db.updateOrderDetails(20, Arrays.asList(1), Arrays.asList(2));


//        db.deleteAuthor(7);
//        db.deleteCustomer(4);
//        db.deleteOrder(21);

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