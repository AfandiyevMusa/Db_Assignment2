import models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbFunctions {
    private static final String dbname = "bookstore";
    private static final String user = "postgres";
    private static final String pass = "pg_strong_password";


    public Connection connect_to_db() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }



    public void insertCustomer(Customer customer) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO customers (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer inserted successfully");
            } else {
                System.out.println("Failed to insert customer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> retrieveAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = connect_to_db();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                // Create a Customer object
                Customer customer = new Customer(customerId, firstName, lastName, email, phone);

                // Add the customer to the list
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public List<Book> retrieveAllBooksWithAuthorsAndOrders() {
        List<Book> booksWithAuthorsAndOrders = new ArrayList<>();

        try (Connection conn = connect_to_db();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT b.book_id, b.title, b.genre, b.price, b.stock_quantity, b.author_id, a.author_name, o.order_id, o.order_date, o.total_amount, a.birth_date AS author_birth_date, a.country AS author_country " +
                             "FROM books b " +
                             "JOIN authors a ON b.author_id = a.author_id " +
                             "LEFT JOIN order_details od ON b.book_id = od.book_id " +
                             "LEFT JOIN orders o ON od.order_id = o.order_id")) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");
                int authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name");
                int orderId = rs.getInt("order_id");
                Date orderDate = rs.getDate("order_date");
                LocalDate localOrderDate = (orderDate != null) ? orderDate.toLocalDate() : null;
                double totalAmount = rs.getDouble("total_amount");
                Date authorBirthDate = rs.getDate("author_birth_date");
                LocalDate localauthorBirthDate = (authorBirthDate != null) ? authorBirthDate.toLocalDate() : null;
                String authorCountry = rs.getString("author_country");

                // Create an Author object
                Author author = new Author(authorId, authorName, localauthorBirthDate, authorCountry);

                // Create an Order object
                Order order = new Order(orderId, localOrderDate, totalAmount);

                // Create a Book object with Author and Order information
                Book book = new Book(bookId, title, genre, price, stockQuantity, author, order);

                // Add the book to the list
                booksWithAuthorsAndOrders.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksWithAuthorsAndOrders;
    }



    // This is update part

    //Update book
//    public void updateBook(int bookId, String newTitle, String newGenre, double newPrice, int newStockQuantity, int newAuthorId) {
//        try (Connection conn = connect_to_db()) {
//            // Retrieve the existing book based on the provided bookId
//            Book existingBook = findBookById(bookId);
//
//            if (existingBook == null) {
//                System.out.println("Book not found");
//                return;
//            }
//
//            if (!" ".equals(newTitle)) {
//                existingBook.setTitle(newTitle);
//            }
//            if (!" ".equals(newGenre)) {
//                existingBook.setGenre(newGenre);
//            }
//            if (newPrice >= 0) {
//                existingBook.setPrice(newPrice);
//            }
//            if (newStockQuantity >= 0) {
//                existingBook.setStockQuantity(newStockQuantity);
//            }
//
//            // If a new authorId is provided, update the author of the book
//            if (newAuthorId > 0) {
//                Author newAuthor = findAuthorById(newAuthorId);
//                existingBook.setAuthor(newAuthor);
//            }
//
//            // Update the book in the database
//            updateBookInDatabase(existingBook);
//
//            System.out.println("Book updated successfully");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Book findBookById(int bookId) throws SQLException {
//        try (Connection conn = connect_to_db();
//             PreparedStatement pstmt = conn.prepareStatement("SELECT b.*, a.* FROM books b JOIN authors a ON b.author_id = a.author_id WHERE b.book_id = ?")) {
//            pstmt.setInt(1, bookId);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    // Populate and return the book object
//                    return extractBookFromResultSet(rs);
//                }
//            }
//        }
//        return null;
//    }
//
//    public void updateBookInDatabase(Book book) throws SQLException {
//        try (Connection conn = connect_to_db();
//             PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET title = ?, genre = ?, price = ?, stock_quantity = ?, author_id = ? WHERE book_id = ?")) {
//            pstmt.setString(1, book.getTitle());
//            pstmt.setString(2, book.getGenre());
//            pstmt.setDouble(3, book.getPrice());
//            pstmt.setInt(4, book.getStockQuantity());
//            pstmt.setInt(5, book.getAuthor().getAuthorId());
//            pstmt.setInt(6, book.getBookId());
//
//            pstmt.executeUpdate();
//        }
//    }
//
//    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
//        int bookId = rs.getInt("book_id");
//        String title = rs.getString("title");
//        String genre = rs.getString("genre");
//        double price = rs.getDouble("price");
//        int stockQuantity = rs.getInt("stock_quantity");
//
//        // Extract author attributes and create an Author object
//        int authorId = rs.getInt("author_id");
//        String authorName = rs.getString("author_name");
//        LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
//        String country = rs.getString("country");
//        Author author = new Author(authorId, authorName, birthDate, country);
//
//        // Create and return the Book object
//        return new Book(bookId, title, genre, price, stockQuantity, author);
//    }



    public void updateAuthor(int authorId, String newAuthorName, String newBirthDate, String newCountry) throws SQLException {
        // Retrieve existing author
        Author existingAuthor = findAuthorById(authorId);

        if (existingAuthor == null) {
            System.out.println("Author with ID " + authorId + " not found.");
            return;
        }

        // Update attributes based on provided values
        if (!" ".equals(newAuthorName)) {
            existingAuthor.setAuthorName(newAuthorName);
        }
        if (!" ".equals(newBirthDate)) {
            // Parse the newBirthDate string to LocalDate and set the birthDate
            existingAuthor.setBirthDate(LocalDate.parse(newBirthDate));
        }
        if (!" ".equals(newCountry)) {
            existingAuthor.setCountry(newCountry);
        }

        // Update the author in the database
        try (Connection conn = connect_to_db()) {
            String updateQuery = "UPDATE authors SET author_name = ?, birth_date = ?, country = ? WHERE author_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, existingAuthor.getAuthorName());
                pstmt.setDate(2, java.sql.Date.valueOf(existingAuthor.getBirthDate()));
                pstmt.setString(3, existingAuthor.getCountry());
                pstmt.setInt(4, authorId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Author updated successfully");
                } else {
                    System.out.println("Failed to update author");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Author findAuthorById(int authorId) throws SQLException {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM authors WHERE author_id = ?")) {
            pstmt.setInt(1, authorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Extract author attributes and create an Author object
                    String authorName = rs.getString("author_name");
                    LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                    String country = rs.getString("country");

                    return new Author(authorId, authorName, birthDate, country);
                }
            }
        }
        return null;
    }

    public void updateCustomer(int customerId, String newFirstName, String newLastName,
                               String newEmail, String newPhone) {
        try (Connection conn = connect_to_db()) {
            // First, retrieve the existing customer to check for changes
            Customer existingCustomer = findCustomerById(customerId);

            if (existingCustomer == null) {
                System.out.println("Customer not found with ID: " + customerId);
                return;
            }

            // Build the SQL query only for the fields that have changed
            StringBuilder queryBuilder = new StringBuilder("UPDATE customers SET");

            if (!newFirstName.equals(existingCustomer.getFirstName())) {
                queryBuilder.append(" first_name = ?,");
            }
            if (!newLastName.equals(existingCustomer.getLastName())) {
                queryBuilder.append(" last_name = ?,");
            }
            if (!newEmail.equals(existingCustomer.getEmail())) {
                queryBuilder.append(" email = ?,");
            }
            if (!newPhone.equals(existingCustomer.getPhone())) {
                queryBuilder.append(" phone = ?,");
            }

            // Remove the trailing comma
            if (queryBuilder.charAt(queryBuilder.length() - 1) == ',') {
                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            }

            queryBuilder.append(" WHERE customer_id = ?");

            try (PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {
                int parameterIndex = 1;


                if (!" ".equals(newFirstName)) {
                    existingCustomer.setFirstName(newFirstName);
                }
                if (!" ".equals(newLastName)) {
                    existingCustomer.setLastName(newLastName);
                }
                if (!" ".equals(newEmail)) {
                    existingCustomer.setEmail(newEmail);
                }
                if (!" ".equals(newPhone)) {
                    existingCustomer.setPhone(newPhone);
                }

                pstmt.setInt(parameterIndex, customerId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Customer updated successfully");
                } else {
                    System.out.println("Failed to update customer");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer findCustomerById(int customerId) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?")) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("customer_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");

                    return new Customer(id, firstName, lastName, email, phone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // This is delete-part
//    public void deleteBook(int bookId) {
//        try (Connection conn = connect_to_db();
//             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE book_id = ?")) {
//            pstmt.setInt(1, bookId);
//
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                System.out.println("Book deleted successfully");
//            } else {
//                System.out.println("Failed to delete book");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void deleteAuthor(int authorId) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM authors WHERE author_id = ?")) {
            pstmt.setInt(1, authorId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Author deleted successfully");
            } else {
                System.out.println("Failed to delete author");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCustomer(int customerId) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?")) {
            pstmt.setInt(1, customerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer deleted successfully");
            } else {
                System.out.println("Failed to delete customer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteOrder(int orderId) {
        try (Connection conn = connect_to_db()) {
            conn.setAutoCommit(false);

            // Delete entries from order_details
            deleteOrderDetails(conn, orderId);

            // Update stock quantity in books table
            updateStockQuantityForDeletedOrder(conn, orderId);

            // Delete the order itself
            deleteOrderFromOrders(conn, orderId);

            // Commit the transaction
            conn.commit();
            System.out.println("Order deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete order. Rolling back transaction.");
        }
    }

    private void deleteOrderDetails(Connection conn, int orderId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM order_details WHERE order_id = ?")) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }

    private void updateStockQuantityForDeletedOrder(Connection conn, int orderId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE books " +
                        "SET stock_quantity = stock_quantity + od.quantity " +
                        "FROM order_details od " +
                        "WHERE books.book_id = od.book_id AND od.order_id = ?")) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }

    private void deleteOrderFromOrders(Connection conn, int orderId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM orders WHERE order_id = ?")) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }

    public void updateOrderDetails(int orderId, List<Integer> newBookIds, List<Integer> newQuantities) {
        try (Connection conn = connect_to_db()) {
            conn.setAutoCommit(false);

            // Retrieve existing order details
            List<OrderDetail> existingOrderDetails = getOrderDetailsByOrderId(conn, orderId);

            if (existingOrderDetails != null && !existingOrderDetails.isEmpty()) {
                // Update order details based on the provided lists
                for (int i = 0; i < existingOrderDetails.size(); i++) {
                    int orderDetailId = existingOrderDetails.get(i).getOrderDetailId();
                    int updatedBookId = (i < newBookIds.size()) ? newBookIds.get(i) : existingOrderDetails.get(i).getBookId();
                    int updatedQuantity = (i < newQuantities.size()) ? newQuantities.get(i) : existingOrderDetails.get(i).getQuantity();

                    // Update each order detail
                    updateOrderDetailInDatabase(conn, orderDetailId, updatedBookId, updatedQuantity);
                }

                // Commit the transaction
                conn.commit();
                System.out.println("Order details updated successfully.");
            } else {
                System.out.println("No order details found for the specified order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update order details. Rolling back transaction.");
        }
    }

    private List<OrderDetail> getOrderDetailsByOrderId(Connection conn, int orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM order_details WHERE order_id = ?")) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("book_id"),
                        rs.getInt("quantity")
                );
                orderDetails.add(orderDetail);
            }
        }
        return orderDetails;
    }

    private void updateOrderDetailInDatabase(Connection conn, int orderDetailId, int newBookId, int newQuantity) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE order_details SET book_id = ?, quantity = ? WHERE order_detail_id = ?")) {
            pstmt.setInt(1, newBookId);
            pstmt.setInt(2, newQuantity);
            pstmt.setInt(3, orderDetailId);
            pstmt.executeUpdate();
        }
    }

}