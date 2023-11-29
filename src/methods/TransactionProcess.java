package methods;

import models.OrderRequest;

import java.sql.*;

public class TransactionProcess {
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

}
