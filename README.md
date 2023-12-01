Database | Assignment II

**Console Application for Bookstore Management**

**Overview:**
    This Java console application serves as a bookstore management system, allowing users to perform various operations related to books, authors, customers, orders, and transactions. The application interacts with a PostgreSQL database to store and retrieve information.

**Features:**

1. Book Management:
    Create a new book, providing details such as title, genre, price, stock quantity, and author ID.
    View a list of all books.
    Display detailed information about a specific book, including associated orders.
2. Author Management:
    Create a new author, specifying details like name, birth date, and country.
    View a list of all authors.
    Update author information.
    Delete an author.
3. Customer Management:
    Create a new customer with first name, last name, email, and phone details.
    View a list of all customers.
    Update customer information.
    Delete a customer.
4. Order Management:
    Delete an order, including associated order details.
    Update order details for a specific order.
5. Transaction Processing:
    Place a new order, considering available stock quantities.
    Calculate and update the total amount for an order.
6. Metadata Information:
    Display information about tables in the database, including primary and foreign keys.
    Display detailed column information for a specific table.

**Prerequisites**

Ensure the following software is installed on your system:

Java
PostgreSQL
PgAdmin4

Setup

1. Clone the repository:
   git clone https://github.com/yourusername/yourproject.git
   cd yourproject
2. Setup the PostgreSQL database and configure the connection details in DbFunctions class.
3. Run the application:
   java -jar yourproject.jar

**Usage**

Follow the on-screen menu to perform various operations:

1. Create, view, update, and delete books, authors, customers, and orders.
2. Place new orders and manage order details.
3. Display metadata information about tables and columns in the database.
