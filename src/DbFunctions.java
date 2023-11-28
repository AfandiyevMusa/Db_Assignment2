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

    public void insertBook(Book book) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO books (title, genre, price, stock_quantity, author_id) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getGenre());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getStockQuantity());
            pstmt.setInt(5, book.getAuthor().getAuthorId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Book inserted successfully");
            } else {
                System.out.println("Failed to insert book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAuthor(Author author) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO authors (author_name, birth_date, country) VALUES (?, ?, ?)")) {
            pstmt.setString(1, author.getAuthorName());
            pstmt.setDate(2, Date.valueOf(author.getBirthDate())); // Assuming birthDate is in the format "YYYY-MM-DD"
            pstmt.setString(3, author.getCountry());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Author inserted successfully");
            } else {
                System.out.println("Failed to insert author");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // This is retrieve part
    public List<Book> retrieveAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = connect_to_db();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");

                // Create a Book object
                Book book = new Book(bookId, title, genre, price, stockQuantity);

                // Add the book to the list
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Author> retrieveAllAuthors() {
        List<Author> authors = new ArrayList<>();

        try (Connection conn = connect_to_db();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM authors")) {

            while (rs.next()) {
                int authorId = rs.getInt("author_id");
                String authorName = rs.getString("author_name");
                Date birthDate = rs.getDate("birth_date");
                String country = rs.getString("country");

                // Create an Author object
                Author author = new Author(authorId, authorName, birthDate.toLocalDate(), country);

                // Add the author to the list
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
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
    public void updateBook(int bookId, String newTitle, String newGenre, double newPrice, int newStockQuantity, int newAuthorId) {
        try (Connection conn = connect_to_db()) {
            // Retrieve the existing book based on the provided bookId
            Book existingBook = findBookById(bookId);

            if (existingBook == null) {
                System.out.println("Book not found");
                return;
            }

            if (!" ".equals(newTitle)) {
                existingBook.setTitle(newTitle);
            }
            if (!" ".equals(newGenre)) {
                existingBook.setGenre(newGenre);
            }
            if (newPrice >= 0) {
                existingBook.setPrice(newPrice);
            }
            if (newStockQuantity >= 0) {
                existingBook.setStockQuantity(newStockQuantity);
            }

            // If a new authorId is provided, update the author of the book
            if (newAuthorId > 0) {
                Author newAuthor = findAuthorById(newAuthorId);
                existingBook.setAuthor(newAuthor);
            }

            // Update the book in the database
            updateBookInDatabase(existingBook);

            System.out.println("Book updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book findBookById(int bookId) throws SQLException {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("SELECT b.*, a.* FROM books b JOIN authors a ON b.author_id = a.author_id WHERE b.book_id = ?")) {
            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Populate and return the book object
                    return extractBookFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public void updateBookInDatabase(Book book) throws SQLException {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET title = ?, genre = ?, price = ?, stock_quantity = ?, author_id = ? WHERE book_id = ?")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getGenre());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getStockQuantity());
            pstmt.setInt(5, book.getAuthor().getAuthorId());
            pstmt.setInt(6, book.getBookId());

            pstmt.executeUpdate();
        }
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        int bookId = rs.getInt("book_id");
        String title = rs.getString("title");
        String genre = rs.getString("genre");
        double price = rs.getDouble("price");
        int stockQuantity = rs.getInt("stock_quantity");

        // Extract author attributes and create an Author object
        int authorId = rs.getInt("author_id");
        String authorName = rs.getString("author_name");
        LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
        String country = rs.getString("country");
        Author author = new Author(authorId, authorName, birthDate, country);

        // Create and return the Book object
        return new Book(bookId, title, genre, price, stockQuantity, author);
    }



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
    public void deleteBook(int bookId) {
        try (Connection conn = connect_to_db();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE book_id = ?")) {
            pstmt.setInt(1, bookId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Book deleted successfully");
            } else {
                System.out.println("Failed to delete book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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




    // These methods work for 4th step.
//    public void placeOrder(int customerId, int bookId, int quantity) {
//        try (Connection conn = connect_to_db()) {
//            conn.setAutoCommit(false);
//
//            // Check if enough stock is available
//            int availableStock = checkAvailableStock(conn, bookId);
//            if (availableStock >= quantity) {
//                // Insert order and order details
//                int orderId = insertOrder(conn, customerId);
//                insertOrderDetails(conn, orderId, bookId, quantity);
//
//                // Update the total_amount based on the order details
//                updateOrderTotalAmount(conn, orderId);
//
//                // Update stock quantity
//                updateStockQuantity(conn, bookId, availableStock - quantity);
//
//                // Commit the transaction
//                conn.commit();
//                System.out.println("Order placed successfully.");
//            } else {
//                System.out.println("Not enough stock available for the order.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Failed to place the order. Rolling back transaction.");
//        }
//    }

    public void placeOrder(OrderRequest orderRequest) {
        try (Connection conn = connect_to_db()) {
            conn.setAutoCommit(false);

            // Iterate through book IDs and quantities
            for (int i = 0; i < orderRequest.getBookIds().size(); i++) {
                int bookId = orderRequest.getBookIds().get(i);
                int quantity = orderRequest.getQuantities().get(i);

                // Check if enough stock is available
                int availableStock = checkAvailableStock(conn, bookId);

                if (availableStock >= quantity) {
                    // Insert order and order details
                    int orderId = insertOrder(conn, orderRequest.getCustomerId());
                    insertOrderDetails(conn, orderId, bookId, quantity);

                    // Update the total_amount based on the order details
                    updateOrderTotalAmount(conn, orderId);

                    // Update stock quantity
                    updateStockQuantity(conn, bookId, availableStock - quantity);
                } else {
                    System.out.println("Not enough stock available for the order.");
                    conn.rollback();
                    return; // Exit the method if any order fails
                }
            }

            // Commit the transaction if all orders are successful
            conn.commit();
            System.out.println("Order placed successfully for customer " + orderRequest.getCustomerId());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to place the order. Rolling back transaction.");
        }
    }

    private int insertOrder(Connection conn, int customerId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO orders (customer_id, order_date, total_amount) VALUES (?, CURRENT_DATE, 0) RETURNING order_id")) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("order_id");
            } else {
                throw new SQLException("Failed to insert order.");
            }
        }
    }
    private void updateOrderTotalAmount(Connection conn, int orderId) throws SQLException {
        double totalAmount = calculateTotalAmount(conn, orderId);

        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE orders SET total_amount = ? WHERE order_id = ?")) {
            pstmt.setDouble(1, totalAmount);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
        }
    }
    private double calculateTotalAmount(Connection conn, int orderId) throws SQLException {
        double totalAmount = 0.0;

        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT od.quantity, b.price " +
                        "FROM order_details od " +
                        "JOIN books b ON od.book_id = b.book_id " +
                        "WHERE od.order_id = ?")) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                totalAmount += quantity * price;
            }
        }

        return totalAmount;
    }
    private int checkAvailableStock(Connection conn, int bookId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT stock_quantity FROM books WHERE book_id = ?")) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock_quantity");
            } else {
                throw new SQLException("Book not found.");
            }
        }
    }
    // Helper method to insert order details into the OrderDetails table
    private void insertOrderDetails(Connection conn, int orderId, int bookId, int quantity) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO order_details (order_id, book_id, quantity) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        }
    }
    private void updateStockQuantity(Connection conn, int bookId, int newStockQuantity) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE books SET stock_quantity = ? WHERE book_id = ?")) {
            pstmt.setInt(1, newStockQuantity);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        }
    }



    // Accessing metadata (5th step)
    // a. Display names and structures of the tables in the DB
    public void displayTablesInfo() {
        try (Connection conn = connect_to_db()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Display details on columns of the table
                displayColumnsInfo(conn, tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // b. Display details on columns of tables
    public void displayColumnsInfo(Connection conn, String tableName) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                boolean isNullable = columns.getBoolean("IS_NULLABLE");

                System.out.println("  Column: " + columnName +
                        ", Type: " + dataType +
                        ", Size: " + columnSize +
                        ", Nullable: " + (isNullable ? "Yes" : "No"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // c. Display information on primary and foreign keys
    public void displayKeysInfo(Connection conn, String tableName) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            // Display primary keys
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            System.out.print("Primary Keys: ");
            while (primaryKeys.next()) {
                String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                System.out.print(primaryKeyColumn + " ");
            }
            System.out.println();

            // Display foreign keys
            ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
            System.out.print("Foreign Keys: ");
            while (foreignKeys.next()) {
                String foreignKeyColumn = foreignKeys.getString("FKCOLUMN_NAME");
                String referencedTable = foreignKeys.getString("PKTABLE_NAME");
                String referencedColumnName = foreignKeys.getString("PKCOLUMN_NAME");

                System.out.print(foreignKeyColumn + " (References " + referencedTable +
                        "." + referencedColumnName + ") ");
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}