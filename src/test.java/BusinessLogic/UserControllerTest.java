package BusinessLogic;

import DAO.*;
import DomainModel.Booking;
import DomainModel.Field;
import DomainModel.Membership.*;
import DomainModel.SportsCentre;
import DomainModel.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class UserControllerTest {
    private BookingController bookingController;
    private FieldController fieldController;
    private SportsCentreController sportsCentreController;
    private UserController userController;
    private String date;
    private String time;
    private User user1;
    private SportsCentre sportsCentre1;
    private Field field1;
    private Booking booking1;
    private UserDAO userDAO;
    BookingDAO bookingDAO;


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
    public void initDb() throws Exception {
        Class.forName("org.sqlite.JDBC");

        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        // Delete data from all tables
        List<String> tables = Arrays.asList("sportsCentres", "staff", "memberships", "users", "fields", "bookings");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        //Insert some test data
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().truncatedTo(ChronoUnit.HOURS).toString();

        // Create DAOs
        SportsCentreDAO sportsCentreDAO = new SportsCentreDAO();
        MembershipDAO membershipDAO = new MembershipDAO();
        userDAO = new UserDAO(membershipDAO);
        FieldDAO fieldDAO = new FieldDAO(sportsCentreDAO);
        bookingDAO = new BookingDAO(userDAO, fieldDAO);

        // create Controllers
        this.bookingController = new BookingController(bookingDAO, userDAO, fieldDAO);
        this.fieldController = new FieldController(fieldDAO);
        this.sportsCentreController = new SportsCentreController(sportsCentreDAO, fieldController);
        this.userController = new UserController(userDAO, bookingController);

        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (1, 'SportCentre1', 'address1','CAP1','NONE')").executeUpdate();
        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (2, 'SportCentre1', 'address2','CAP2','STUDENT' )").executeUpdate();

        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (1, 'sport1', 4, 8, 15, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (2, 'sport2', 3, 9, 20, 0, 2)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (3, 'sport3', 3, 9, 25, 1, 1)").executeUpdate();

        Membership m1 = new Free();
        membershipDAO.save(m1);
        Membership m2 = new Basic(m1);
        membershipDAO.save(m2);
        Membership m3 = new Student(m2);
        membershipDAO.save(m3);
        Membership m4 = new Premium(m2);
        membershipDAO.save(m4);

        sportsCentre1 = new SportsCentre(1, "SportCentre1", "address1", "CAP1", "NONE");
        user1 = new User(4, "codFisc4", "name4", "surname4", LocalDate.parse(this.date), m4);
        field1 = new Field(4, "sport4", 3, 9, 25, true, sportsCentre1);

        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (1, 'codFisc1', 'name1', 'surname1','" + this.date + "', 'Basic')").executeUpdate();
        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (2, 'codFisc2', 'name2', 'surname2','" + LocalDate.now() + "', 'Student')").executeUpdate();
        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (3, 'codFisc3', 'name3', 'surname3','" + this.date + "', 'Premium')").executeUpdate();

        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (1, '" + this.date + "', '" + this.time + "', 0, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (2, '" + LocalDate.now() + "', '" + LocalTime.now() + "', 1, 2, 2)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (3, '" + LocalDate.now().plusDays(2) + "', '" + LocalTime.now().plusHours(5) + "', 0, 3, 3)").executeUpdate();

        booking1 = new Booking(1, LocalDate.parse(this.date), 2, LocalTime.parse(this.time), user1, field1);
        connection.close();
    }

    @Test
    public void testSubscribeMember() throws SQLException {
        Assertions.assertDoesNotThrow(() -> userController.subscribeMember(user1));
        Assertions.assertEquals(4, userDAO.getAll().size());
    }

    @Test
    public void testUnsubscribeMember() throws SQLException {
        Assertions.assertDoesNotThrow(() -> userController.unsubscribeMember(1));
        Assertions.assertEquals(2, userDAO.getAll().size());
    }

    @Test
    public void testGetMember() throws SQLException {
        userController.subscribeMember(user1);
        Assertions.assertEquals(userDAO.get(4).getID(), userController.getMember(4).getID());
    }

    @Test
    public void testChangeMembership() throws SQLException {
        Assertions.assertDoesNotThrow(() -> userController.changeMembership(1, 3));
        Assertions.assertEquals("Premium", userController.getMember(1).getMembershipName());
    }

    @Test
    public void testAddBooking() throws SQLException {
        userController.subscribeMember(user1);
        Assertions.assertDoesNotThrow(() -> userController.addBooking(booking1));
        Assertions.assertEquals(4, bookingDAO.getAll().size());
    }

    @Test
    public void testRemoveBooking() throws SQLException {
        Assertions.assertDoesNotThrow(() -> userController.removeBooking(3, 3));
        Assertions.assertEquals(2, bookingDAO.getAll().size());
    }

    @Test
    public void TestModifyBooking() throws SQLException {
        LocalDate date1 = LocalDate.now();
        LocalTime time1 = LocalTime.now().plusHours(5);
        Assertions.assertDoesNotThrow(() -> userController.modifyBooking(3, date1, time1));
        Assertions.assertEquals(date1, bookingDAO.get(3).getDate());
        Assertions.assertEquals(time1, bookingDAO.get(3).getTime());

    }


}