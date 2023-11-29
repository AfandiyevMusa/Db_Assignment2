package methods;

import models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerOperations {
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

                if (!newFirstName.equals(existingCustomer.getFirstName())) {
                    pstmt.setString(parameterIndex++, newFirstName);
                }
                if (!newLastName.equals(existingCustomer.getLastName())) {
                    pstmt.setString(parameterIndex++, newLastName);
                }
                if (!newEmail.equals(existingCustomer.getEmail())) {
                    pstmt.setString(parameterIndex++, newEmail);
                }
                if (!newPhone.equals(existingCustomer.getPhone())) {
                    pstmt.setString(parameterIndex++, newPhone);
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
}
