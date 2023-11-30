import methods.*;
import models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static MetaData metaData = new MetaData();
    static TransactionProcess transactionProcess = new TransactionProcess();
    static BookOperations bookOperations = new BookOperations();
    static AuthorOperations authorOperations = new AuthorOperations();
    static CustomerOperations customerOperations = new CustomerOperations();
    static OrderOperations orderOperations = new OrderOperations();
    public static void main(String[] args) throws SQLException {
        DbFunctions db = new DbFunctions();
        Connection conn=db.connect_to_db();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createBook();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    showBookInfoAndOrders();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    createAuthor();
                    break;
                case 7:
                    showAllAuthors();
                    break;
                case 8:
                    updateAuthor();
                    break;
                case 9:
                    deleteAuthor();
                    break;
                case 10:
                    createCustomer();
                    break;
                case 11:
                    showAllCustomers();
                    break;
                case 12:
                    updateCustomer();
                    break;
                case 13:
                    deleteCustomer();
                    break;
                case 14:
                    deleteOrder();
                    break;
                case 15:
                    updateOrderDetails(); //not working
                    break;
                case 16:
                    placeOrderFromUser();
                    break;
                case 17:
                    displayTablesInfoWithKeys();
                    break;
                case 18:
                    displayColumnsInfo();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("-------- Console App Menu --------");
        System.out.println("Book Section:");
        System.out.println("1. Create Book");
        System.out.println("2. Show All Books");
        System.out.println("3. Show Book Info and Orders");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
        System.out.println("--------------------------------");
        System.out.println("Author Section:");
        System.out.println("6. Create Author");
        System.out.println("7. Show All Author");
        System.out.println("8. Update Author");
        System.out.println("9. Delete Author");
        System.out.println("--------------------------------");
        System.out.println("Customer Section:");
        System.out.println("10. Create Customer");
        System.out.println("11. Show All Customer");
        System.out.println("12. Update Customer");
        System.out.println("13. Delete Customer");
        System.out.println("--------------------------------");
        System.out.println("Order Section:");
        System.out.println("14. Delete Order");
        System.out.println("15. Update Order Details");
        System.out.println("--------------------------------");
        System.out.println("Transaction Section:");
        System.out.println("16. Transaction order");
        System.out.println("--------------------------------");
        System.out.println("Metadata Section:");
        System.out.println("17. Display Tables Informations:");
        System.out.println("18. Display Columns Informations:");
        System.out.println("--------------------------------");
        System.out.println("Exit:");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book details:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Stock Quantity: ");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Author ID: ");
        int authorId = scanner.nextInt();
        scanner.nextLine();

        Book newBook = new Book(title, genre, price, stockQuantity, authorId);
        bookOperations.insertBook(newBook);

        System.out.println("Book created successfully!");
    }

    private static void showAllBooks() {
        List<Book> books = bookOperations.retrieveAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("All Books:");
            for (Book book : books) {
                System.out.println(book);
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    private static void showBookInfoAndOrders() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (bookId != 0) {
            System.out.println(bookOperations.retrieveBookInfoAndOrdersByBookId(bookId));
        }
    }

    private static void updateBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the book ID to update:");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (bookId != 0) {
            System.out.println("Existing Book Information:");
            System.out.println(bookOperations.retrieveBookInfoAndOrdersByBookId(bookId));

            System.out.println("Enter the updated book information:");

            System.out.print("Title (press Enter to keep the existing title): ");
            String newTitle = getUserInput(scanner);

            System.out.print("Genre (press Enter to keep the existing genre): ");
            String newGenre = getUserInput(scanner);

            System.out.print("Price (press Enter to keep the existing price): ");
            double newPrice = parseDoubleFromUserInput(scanner);

            System.out.print("Stock Quantity (press Enter to keep the existing stock quantity): ");
            int newStockQuantity = parseIntFromUserInput(scanner);

            System.out.print("Author ID (press Enter to keep the existing author): ");
            int newAuthorId = parseIntFromUserInput(scanner);

            bookOperations.updateBook(bookId, newTitle, newGenre, newPrice, newStockQuantity, newAuthorId);

            System.out.println("Book updated successfully.");
        }
    }

    private static String getUserInput(Scanner scanner) {
        String userInput = scanner.nextLine().trim();
        return userInput.isEmpty() ? " " : userInput;
    }

    private static double parseDoubleFromUserInput(Scanner scanner) {
        try {
            String userInput = getUserInput(scanner);
            return userInput.isEmpty() ? -1 : Double.parseDouble(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Keeping the existing value.");
            return -1;
        }
    }

    private static int parseIntFromUserInput(Scanner scanner) {
        try {
            String userInput = getUserInput(scanner);
            return userInput.isEmpty() ? -1 : Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Keeping the existing value.");
            return -1;
        }
    }


    private static void deleteBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the book ID to delete:");
        int bookId = scanner.nextInt();

        // Check if the book ID is valid
        if (bookId != 0) {
            // Call the deleteBook method with the provided book ID
            bookOperations.deleteBook(bookId);

            System.out.println("Book deleted successfully.");
        }
    }

    private static void createAuthor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter author details:");

        System.out.print("Author Name: ");
        String authorName = scanner.nextLine();

        System.out.print("Birth Date (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateStr);

        System.out.print("Country: ");
        String country = scanner.nextLine();

        Author newAuthor = new Author(authorName, birthDate, country);
        authorOperations.insertAuthor(newAuthor);

        System.out.println("Author created successfully!");
    }

    private static void showAllAuthors() {
        List<Author> authors = authorOperations.retrieveAllAuthors();

        if (authors.isEmpty()) {
            System.out.println("No authors found.");
        } else {
            System.out.println("All Authors:");
            for (Author author : authors) {
                System.out.println(author);
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    private static void updateAuthor() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the author ID to update:");
        int authorId = scanner.nextInt();
        scanner.nextLine();

        if (authorId != 0) {
            System.out.println("Existing Author Information:");
            System.out.println(authorOperations.retrieveAuthorById(authorId));

            System.out.println("Enter the updated author information:");

            System.out.print("Author Name (press Enter to keep the existing name): ");
            String newAuthorName = getUserInput(scanner);

            System.out.print("Birth Date (YYYY-MM-DD, press Enter to keep the existing birth date): ");
            String newBirthDateStr = getUserInput(scanner);
            LocalDate newBirthDate = parseLocalDateFromUserInput(newBirthDateStr);

            System.out.print("Country (press Enter to keep the existing country): ");
            String newCountry = getUserInput(scanner);

            authorOperations.updateAuthor(authorId, newAuthorName, newBirthDate, newCountry);

            System.out.println("Author updated successfully.");
        }
    }

    private static LocalDate parseLocalDateFromUserInput(String userInput) {
        try {
            return userInput.isEmpty() ? null : LocalDate.parse(userInput);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Keeping the existing value.");
            return null;
        }
    }

    private static void deleteAuthor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the author ID to delete:");
        int authorId = scanner.nextInt();

        // Check if the author ID is valid
        if (authorId != 0) {
            // Call the deleteAuthor method with the provided author ID
            authorOperations.deleteAuthor(authorId);

            System.out.println("Author deleted successfully.");
        }
    }

    private static void createCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer details:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Customer newCustomer = new Customer(firstName, lastName, email, phone);
        customerOperations.insertCustomer(newCustomer);

        System.out.println("Customer created successfully!");
    }

    private static void showAllCustomers() {
        List<Customer> customers = customerOperations.retrieveAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : customers) {
                System.out.println(customer);
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    private static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the customer ID to update:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        if (customerId != 0) {
            System.out.println("Existing Customer Information:");
            System.out.println(customerOperations.retrieveCustomerById(customerId));

            System.out.println("Enter the updated customer information:");

            System.out.print("First Name (press Enter to keep the existing first name): ");
            String newFirstName = getUserInput(scanner);

            System.out.print("Last Name (press Enter to keep the existing last name): ");
            String newLastName = getUserInput(scanner);

            System.out.print("Email (press Enter to keep the existing email): ");
            String newEmail = getUserInput(scanner);

            System.out.print("Phone (press Enter to keep the existing phone): ");
            String newPhone = getUserInput(scanner);

            customerOperations.updateCustomer(customerId, newFirstName, newLastName, newEmail, newPhone);

            System.out.println("Customer updated successfully.");
        }
    }

    private static void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the customer ID to delete:");
        int customerId = scanner.nextInt();

        // Check if the customer ID is valid
        if (customerId != 0) {
            // Call the deleteCustomer method with the provided customer ID
            customerOperations.deleteCustomer(customerId);

            System.out.println("Customer deleted successfully.");
        }
    }

    private static void deleteOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Order ID to delete:");
        int orderId = scanner.nextInt();

        if (orderId != 0) {
            orderOperations.deleteOrder(orderId);
        }
    }

    private static void updateOrderDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the order ID to update:");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Check if the order ID is valid
        if (orderId != 0) {
            // Retrieve the existing order details
            List<OrderDetail> existingOrderDetails = orderOperations.getOrderDetailsByOrderId(orderId);

            if (existingOrderDetails != null && !existingOrderDetails.isEmpty()) {
                // Display existing order details
                System.out.println("Existing Order Details:");
                for (OrderDetail orderDetail : existingOrderDetails) {
                    System.out.println(orderDetail);
                }

                // Prompt the user for updated information
                System.out.println("Enter the updated order details:");

                List<Integer> newBookIds = new ArrayList<>();
                List<Integer> newQuantities = new ArrayList<>();

                // Get updated book IDs and quantities
                boolean continueUpdating = true;
                while (continueUpdating) {
                    System.out.print("Enter Book ID (0 to stop): ");
                    int bookId = scanner.nextInt();
                    if (bookId == 0) {
                        continueUpdating = false;
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    newBookIds.add(bookId);
                    newQuantities.add(quantity);
                }

                // Update the order details with the new information
                orderOperations.updateOrderDetails(orderId, newBookIds, newQuantities);

                System.out.println("Order details updated successfully.");
            } else {
                System.out.println("No order details found for the specified order.");
            }
        }
    }


    private static void placeOrderFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter the number of different orders: ");
        int numBooks = scanner.nextInt();

        List<Integer> bookIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        // Get book IDs and quantities from the user
        for (int i = 0; i < numBooks; i++) {
            System.out.print("Enter Book ID for item " + (i + 1) + ": ");
            int bookId = scanner.nextInt();

            System.out.print("Enter quantity for item " + (i + 1) + ": ");
            int quantity = scanner.nextInt();

            bookIds.add(bookId);
            quantities.add(quantity);
        }

        OrderRequest orderRequest = new OrderRequest(customerId, bookIds, quantities);

        transactionProcess.placeOrder(orderRequest);
    }


    private static void displayTablesInfoWithKeys(){
        metaData.displayTablesInfoWithKeys();
    }

    private  static  void displayColumnsInfo(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Book Title: ");
        String bookTitle = scanner.nextLine();

        if (bookTitle.equals("books") || bookTitle.equals("authors") || bookTitle.equals("customer") || bookTitle.equals("order_details") || bookTitle.equals("orders")) {
            metaData.displayColumnsInfo(metaData.connect_to_db(), bookTitle);
        }else {
            System.out.println("We don't have any information for the provided book title.");
            displayColumnsInfo();
        }
    }
}