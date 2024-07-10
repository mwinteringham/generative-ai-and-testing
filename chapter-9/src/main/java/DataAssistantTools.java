import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.sql.SQLException;

public class DataAssistantTools {

    QueryTools queryTools = new QueryTools();

    public DataAssistantTools() throws SQLException {
    }

    @Tool("Create room records")
    public void createRooms(@P("Amount of room records to create") int count) throws SQLException {
        for(int i = 1; i <= count; i++){
            queryTools.createRoom();
        }
    }

    @Tool("Get most recent roomid from database after rooms have been created")
    public int getRoomId() throws SQLException {
        return queryTools.getRoomId();
    }

    @Tool("Create booking records")
    public void createBookings(@P("Amount of booking records to create") int count, @P("Most recent roomid") int roomid) throws SQLException {
        System.out.println("I will create the bookings for room: " + roomid);
        for(int i = 1; i <= count; i++){
            queryTools.createBooking(roomid);
        }
    }

    @Tool("Show results of database")
    public void displayDatabase() throws SQLException {
        System.out.println("Current ROOM database state:");
        queryTools.outputTables("SELECT * FROM ROOMS");

        System.out.println("Current BOOKING database state:");
        queryTools.outputTables("SELECT * FROM BOOKINGS");
    }

}
