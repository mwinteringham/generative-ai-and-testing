import java.sql.*;

public class QueryTools {

    // Connection object to manage the connection to the database
    private final Connection connection;

    // Constructor to initialize the connection and create the database schema
    public QueryTools() throws SQLException {
        // Establish a connection to an in-memory H2 database called "testdb"
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb");

        // Create a statement to execute SQL commands
        Statement st = connection.createStatement();

        // Create two tables: BOOKINGS and ROOMS with appropriate columns and constraints
        st.executeUpdate("""
            CREATE TABLE BOOKINGS (
                bookingid int NOT NULL AUTO_INCREMENT,   // Unique ID for each booking
                roomid int,                              // Foreign key to the ROOMS table
                firstname varchar(255),                  // First name of the person booking
                lastname varchar(255),                   // Last name of the person booking
                depositpaid boolean,                     // Whether a deposit is paid
                checkin date,                            // Check-in date
                checkout date,                           // Check-out date
                primary key (bookingid)                  // Primary key on bookingid
            );
            CREATE TABLE ROOMS (
                roomid int NOT NULL AUTO_INCREMENT,      // Unique ID for each room
                room_name varchar(255),                  // Name or number of the room
                type varchar(255),                       // Type of room (single, double, etc.)
                beds int,                                // Number of beds in the room
                accessible boolean,                      // Accessibility status of the room
                image varchar(2000),                     // URL to the room's image
                description varchar(2000),               // Description of the room
                features varchar(100) ARRAY,             // Array of room features (TV, WiFi, etc.)
                roomPrice int,                           // Price of the room
                primary key (roomid)                     // Primary key on roomid
            );
        """);
    }

    // Method to create a room entry in the ROOMS table
    public void createRoom() throws SQLException {
        Statement st = connection.createStatement();

        // Insert a new room into the ROOMS table with predefined values
        st.executeUpdate("""
            INSERT INTO ROOMS (room_name, type, beds, accessible, image, description, features, roomPrice) 
            VALUES (
                '101',                  // Room name or number
                'single',               // Room type (single room)
                1,                      // Number of beds
                true,                   // Room is accessible
                '/images/room2.jpg',    // URL of the room's image
                'A generated description',  // Room description
                ARRAY['TV', 'WiFi', 'Safe'], // Room features as an array
                100);                   // Room price
        """);
    }

    // Method to create a booking entry in the BOOKINGS table for a given room
    public void createBooking(int roomid) throws SQLException {
        Statement st = connection.createStatement();

        // Insert a new booking into the BOOKINGS table for a specific room ID
        st.executeUpdate("""
            INSERT INTO BOOKINGS (roomid, firstname, lastname, depositpaid, checkin, checkout)
            VALUES (
                ?,                  // Room ID (foreign key)
                'James',            // First name of the guest
                'Dean',             // Last name of the guest
                true,               // Deposit is paid
                '2022-02-01',       // Check-in date
                '2022-02-05'        // Check-out date
            );
        """.replace("?", Integer.toString(roomid)));  // Replace '?' with the actual roomid value
    }

    // Method to output the results of a query to the console
    public void outputTables(String query) throws SQLException {
        Statement st = connection.createStatement();

        // Execute the query and get the result set
        ResultSet rs = st.executeQuery(query);

        // Get metadata about the result set (such as column names and count)
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // Loop through the result set and print each row of data
        while (rs.next()) {
            for(int i = 1 ; i <= columnsNumber; i++){
                // Print each column value in the current row
                System.out.print(rs.getString(i) + " ");
            }
            // Move to a new line after each row
            System.out.println();
        }
    }

    // Method to retrieve the most recently created room ID
    public int getRoomId() throws SQLException {
        Statement st = connection.createStatement();

        // Execute a query to get the most recent room ID
        ResultSet rs = st.executeQuery("SELECT roomid FROM ROOMS ORDER BY roomid DESC");

        // If a room exists, return its ID; otherwise, return 0
        if(rs.next()){
            return rs.getInt("roomid");
        } else {
            return 0;
        }
    }
}
