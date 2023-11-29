package methods;

import java.sql.*;

public class MetaData {
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

    public void displayTablesInfoWithKeys() {
        try (Connection conn = connect_to_db()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Display details on columns of the table
                displayColumnsInfo(conn, tableName);

                // Display information on primary and foreign keys
                displayKeysInfo(conn, tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void displayColumnsInfo(Connection conn, String tableName) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            // Display details on columns of the table
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            System.out.println("Columns Info for Table: " + tableName);
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

            // Display information on primary keys
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            System.out.print("Primary Keys: ");
            while (primaryKeys.next()) {
                String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                System.out.print(primaryKeyColumn + " ");
            }
            System.out.println();

            // Display information on foreign keys
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
