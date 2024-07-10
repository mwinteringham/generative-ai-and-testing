import java.sql.*;

public class QueryTools {

    private final Connection connection;

    public QueryTools() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        Statement st = connection.createStatement();
        st.executeUpdate("""
            CREATE TABLE BOOKINGS (
                bookingid int NOT NULL AUTO_INCREMENT,
                roomid int,
                firstname varchar(255),
                lastname varchar(255),
                depositpaid boolean,
                checkin date,
                checkout date,
                primary key (bookingid)
            );
            CREATE TABLE ROOMS (
                roomid int NOT NULL AUTO_INCREMENT,
                room_name varchar(255),
                type varchar(255),
                beds int,
                accessible boolean,
                image varchar(2000),
                description varchar(2000),
                features varchar(100) ARRAY,
                roomPrice int,
                primary key (roomid)
            );
        """);
    }

    public void createRoom() throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate("""
            INSERT INTO ROOMS (room_name, type, beds, accessible, image, description, features, roomPrice) 
            VALUES (
                '101', 
                'single',
                1,
                true, 
                '/images/room2.jpg',
                'A generated description',
                ARRAY['TV', 'WiFi', 'Safe'],
                100);
        """);
    }

    public void createBooking(int roomid) throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate("""
            INSERT INTO BOOKINGS (roomid, firstname, lastname, depositpaid, checkin, checkout)
            VALUES (
                ?,
                'James',
                'Dean',
                true,
                '2022-02-01',
                '2022-02-05'
            );
        """.replace("?", Integer.toString(roomid)));
    }

    public void outputTables(String query) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for(int i = 1 ; i <= columnsNumber; i++){
                System.out.print(rs.getString(i) + " ");

            }
            System.out.println();
        }
    }

    public int getRoomId() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT roomid FROM ROOMS ORDER BY roomid DESC");

        if(rs.next()){
            return rs.getInt("roomid");
        } else {
            return 0;
        }
    }
}
