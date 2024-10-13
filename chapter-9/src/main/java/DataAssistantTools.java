import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.sql.SQLException;

public class DataAssistantTools {

    // Instance of QueryTools class used to interact with the database
    QueryTools queryTools = new QueryTools();

    // Constructor that can throw a SQLException, potentially from initializing the QueryTools
    public DataAssistantTools() throws SQLException {
    }

    // Tool method to create room records in the database
    // The annotation @Tool specifies that this method can be invoked as a tool, with 'count' passed by the caller
    @Tool("Create room records")
    public void createRooms(@P("Amount of room records to create") int count) throws SQLException {
        // Loop through the number of rooms to be created, calling the createRoom method for each
        for (int i = 1; i <= count; i++) {
            queryTools.createRoom();  // Interacts with QueryTools to create a new room in the database
        }
    }

    // Tool method to get the most recent room ID from the database
    // This can be useful after room creation to work with the latest room entry
    @Tool("Get most recent roomid from database after rooms have been created")
    public int getRoomId() throws SQLException {
        return queryTools.getRoomId();  // Fetches the most recently created room ID from the database
    }

    // Tool method to create booking records linked to a specific room ID
    // The room ID is passed along with the count of booking records to create
    @Tool("Create booking records")
    public void createBookings(@P("Amount of booking records to create") int count, @P("Most recent roomid") int roomid) throws SQLException {
        // Inform the user which room ID is being used for the bookings
        System.out.println("I will create the bookings for room: " + roomid);

        // Loop through the number of bookings to be created, calling the createBooking method for each
        for (int i = 1; i <= count; i++) {
            queryTools.createBooking(roomid);  // Interacts with QueryTools to create a booking for the given room ID
        }
    }

    // Tool method to display the current state of the ROOM and BOOKING tables in the database
    @Tool("Show results of database")
    public void displayDatabase() throws SQLException {
        // Output all data from the ROOMS table
        System.out.println("Current ROOM database state:");
        queryTools.outputTables("SELECT * FROM ROOMS");

        // Output all data from the BOOKINGS table
        System.out.println("Current BOOKING database state:");
        queryTools.outputTables("SELECT * FROM BOOKINGS");
    }

}
