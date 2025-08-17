package za.co.wethinkcode.robots.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DbTest is a small command-line tool used to check that we can connect to a SQLite database.
 *
 * By default (without any command-line arguments) it attempts to create a SQLite table in an in-memory database.
 * If it succeeds, we assume that all the working parts we need to use SQLite databases are in place and working.
 *
 * The only command-line argument this app understands is
 *  `-f <filename>`
 *  which tells that application to create the test table in a real (disk-resident) database named by the given
 *  filename. Note that the application _does not delete_ the named file, but leaves it in the filesystem
 *  for later examination if desired.
 */
public class DbConnect {
    public static final String IN_MEMORY_DB_URL = "jdbc:sqlite::memory:";
    public static final String DISK_DB_URL = "jdbc:sqlite:";

    public static void main(String[] args) {

        DbConnect app = new DbConnect();
    }

    private String dbUrl = "jdbc:sqlite:robotworld.db";

    private DbConnect() {
        dbUrl =  "jdbc:sqlite:robotworld.db";

        try (final Connection connection = DriverManager.getConnection(dbUrl)) {
            System.out.println("Connected to database ");

            createTables(connection);
            insertSampleData(connection);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
//
//    private void runTest(Connection connection) {
//        createTables(connection);
//
//    }

    public void createTables(Connection  connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS world (" +
                    "id INTEGER PRIMARY KEY CHECK (id = 1), " +
                    "width INTEGER NOT NULL, " +
                    "height INTEGER NOT NULL)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Obstacles (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "X INTEGER NOT NULL, " +
                    "Y INTEGER NOT NULL, " +
                    "width INTEGER NOT NULL, " +
                    "height INTEGER NOT NULL)");

            System.out.println("Tables created successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to create tables: " + e.getMessage());
        }
    }

        public void insertSampleData(Connection connection){
            try (Statement stmt = connection.createStatement()){
                stmt.executeUpdate("INSERT OR REPLACE INTO world(id, width, height) VALUES (1,5,5)");

                stmt.executeUpdate("INSERT INTO obstacles (x,y, width, height) VALUES (1, 1, 2, 1)");
                stmt.executeUpdate("INSERT INTO obstacles(x,y, width, height) VALUES (3,2,1,3)");

                System.out.println("Sample data inserted successfully! ");
            }catch (SQLException e) {
                System.err.println("Failed to insert data: " + e.getMessage());

        }
    }

}
