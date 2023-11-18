package DAO;
import DomainModel.Booking;
import DAO.*;
import DomainModel.*;
import DomainModel.Membership.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingDAOTest {

    private SportsCentreDAO sportsCentreDAO=new SportsCentreDAO();
    private MembershipDAO membershipDAO=new MembershipDAO();
    private UserDAO userDAO=new UserDAO(membershipDAO);
    private FieldDAO fieldDAO=new FieldDAO(sportsCentreDAO);
    private BookingDAO bookingDAO;
    private String date;
    private String time;

    @BeforeAll
    public static void setUpBeforeAll() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite").createStatement();
        stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();
    }

    @BeforeEach
    public void initDb() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        bookingDAO=new BookingDAO(userDAO,fieldDAO);

        // Delete data from lessons table
        List<String> tables = Arrays.asList("sportsCentres", "staff", "memberships", "users", "fields", "bookings");
        for (String table : tables) {
            connection.prepareStatement("DELETE FROM " + table).executeUpdate();
        }

        //Insert some test data
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().truncatedTo(ChronoUnit.HOURS).toString();

        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (1, 'SportCentre1', 'address1','CAP1','NONE')").executeUpdate();
        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (2, 'SportCentre1', 'address2','CAP2','STUDENT' )").executeUpdate();

        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (1, 'sport1', 4, 8, 15, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (2, 'sport2', 3, 9, 20, 1, 2)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (3, 'sport3', 3, 9, 25, 0, 1)").executeUpdate();

        Membership m1=new Free(); membershipDAO.save(m1);
        Membership m2=new Basic(m1); membershipDAO.save(m2);
        Membership m3=new Student(m2); membershipDAO.save(m3);
        Membership m4=new Premium(m2); membershipDAO.save(m4);

        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (1, 'codFisc1', 'name1', 'surname1','"+ this.date +"', 'Basic')").executeUpdate();
        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (2, 'codFisc2', 'name2', 'surname2','"+ LocalDate.now() +"', 'Student')").executeUpdate();
        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (3, 'codFisc3', 'name3', 'surname3','"+ this.date +"', 'Premium')").executeUpdate();


        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (1, '" + this.date + "', '" + this.time + "', 0, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (2, '" + LocalDate.now() + "', '" + LocalTime.now() + "', 1, 2, 2)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (3, '" + LocalDate.now().plusDays(2) + "', '" + LocalTime.now().plusHours(5) + "', 0, 3, 3)").executeUpdate();

        connection.close();
    }



    @Test
    public void testSaveBooking() throws SQLException {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        Assertions.assertDoesNotThrow(() -> bookingDAO.save(booking));
        Assertions.assertEquals(4, bookingDAO.getAll().size());
    }

    @Test
    public void testUpdateBookingSuccess() throws Exception {
        User user=userDAO.get(1);
        Field field=fieldDAO.get(1);
        String [] update={this.date , this.time,"1", "1"}; //modified date and time
        Assertions.assertDoesNotThrow(() -> bookingDAO.modify(bookingDAO.get(1),update));
        Assertions.assertEquals(3, bookingDAO.getAll().size());
    }

    @Test
    public void testRemoveSuccess() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        bookingDAO.save(booking);
        Assertions.assertDoesNotThrow(() -> bookingDAO.remove(4));
        Assertions.assertEquals(3, bookingDAO.getAll().size());
    }

    @Test
    public void testRemoveFail() {
        Assertions.assertThrows(Exception.class, () -> bookingDAO.remove(5));
    }

    @Test
    public void testGet() throws Exception {
        Booking booking=bookingDAO.get(1);
        Assertions.assertEquals(1, booking.getID());
        Assertions.assertEquals(this.date, booking.getDate().toString());
        Assertions.assertEquals(this.time, booking.getTime().toString());
        Assertions.assertEquals(1, booking.getUser().getID());
        Assertions.assertEquals(1, booking.getField().getId());
        Assertions.assertFalse(booking.isPayed());
    }

    @Test
    public void testGetAll() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        bookingDAO.save(booking);
        List<Booking> bookings = bookingDAO.getAll();
        Assertions.assertEquals(4, bookings.size());
        Assertions.assertEquals(1, bookings.get(0).getID());
        Assertions.assertEquals(2, bookings.get(1).getID());
    }


    @Test
    public void testGetBookingsForMember() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        bookingDAO.save(booking);
        ArrayList<Booking> bookings = bookingDAO.getBookingsForMember(2);
        Assertions.assertEquals(2, bookings.size());
    }

    @Test
    public void testGetBookingsForField() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        bookingDAO.save(booking);
        ArrayList<Booking> bookings = bookingDAO.getBookingsForField(2);
        Assertions.assertEquals(2, bookings.size());
    }

    @Test
    public void testGetBookingsForDate() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        bookingDAO.save(booking);
        ArrayList<Booking> bookings = bookingDAO.getBookingsForDate(LocalDate.now());
        Assertions.assertEquals(3, bookings.size());
    }
    @Test
    public void testGetBookingsForDateAndTime() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.parse(this.time) , user, field);
        bookingDAO.save(booking);
        ArrayList<Booking> bookings = bookingDAO.getBookingsForDateAndTime(LocalDate.now(), LocalTime.parse(this.time));
        Assertions.assertEquals(2, bookings.size());
    }
    @Test
    public void testAvailableFieldAtTimeAndDate() throws Exception {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.parse(this.time) , user, field);
        bookingDAO.save(booking);
        Assertions.assertTrue(bookingDAO.availableFieldAtTimeAndDate(2,LocalDate.now(),LocalTime.parse(this.time).plusHours(2)));   //Available
        Assertions.assertFalse(bookingDAO.availableFieldAtTimeAndDate(2,LocalDate.now(),LocalTime.parse(this.time).plusHours(4)));  //Not Available
        Assertions.assertFalse(bookingDAO.availableFieldAtTimeAndDate(1,LocalDate.now().plusDays(1),LocalTime.parse(this.time)));   //Not Available

    }



}
