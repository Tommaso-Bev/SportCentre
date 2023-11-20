package BusinessLogic;

import DAO.*;
import DomainModel.SportsCentre;
import DomainModel.Staff;
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
import java.util.Arrays;
import java.util.List;

public class StaffControllerTest {
    private SportsCentreDAO sportsCentreDAO = new SportsCentreDAO();
    private MembershipDAO membershipDAO = new MembershipDAO();
    private UserDAO userDAO = new UserDAO(membershipDAO);
    private FieldDAO fieldDAO = new FieldDAO(sportsCentreDAO);

    private BookingDAO bd = new BookingDAO(userDAO, fieldDAO);

    private FieldController fc = new FieldController(fieldDAO);

    private SportsCentreController scc = new SportsCentreController(sportsCentreDAO, fc);

    private BookingController bc = new BookingController(bd, userDAO, fieldDAO);
    private StaffDAO sd = new StaffDAO(sportsCentreDAO);
    private StaffController sc;
    private String date;
    private String time;

    private SportsCentre scentre = new SportsCentre(1, "n", "v", "501234", "no");

    @BeforeAll
    public static void setUpBeforeAll() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("org.sqlite.JDBC");
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
    public void initDb() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        sc = new StaffController(scc, bc, sd, fieldDAO);
        List<String> tables = Arrays.asList("sportsCentres", "staff", "memberships", "users", "fields", "bookings");
        for (String table : tables) {
            connection.prepareStatement("DELETE FROM " + table).executeUpdate();
        }

        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();

        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (1, 'SportCentre1', 'address1','CAP1','NONE')").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, sport, minimumPeopleRequired, maximumPeopleRequired, fineph, availability, sportCentre) VALUES (1, 'sport1', 4, 8, 15, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO users (ID, codFisc, firstName, surname, inscriptionDate, membershipsName ) VALUES (1, 'codFisc1', 'name1', 'surname1','" + this.date + "', 'Basic')").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (1, '" + this.date + "', '" + this.time + "', 0, 1, 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (ID, date, time, payed, users, field) VALUES (2, '" + LocalDate.now() + "', '" + LocalTime.now() + "', 1, 1, 2)").executeUpdate();

        connection.close();
    }

    @Test
    public void testHire() throws SQLException {
        Staff staff = new Staff(1, "a", "a", "a", LocalDate.now(), "a", 100, scentre);
        Assertions.assertDoesNotThrow(() -> sc.hireStaff(staff));
        Assertions.assertEquals(1, sd.getAll().size());
    }

    @Test
    public void testFire() throws SQLException {
        sc.hireStaff(new Staff(1, "a", "a", "a", LocalDate.now(), "a", 100, scentre));
        Assertions.assertDoesNotThrow(() -> sc.fireStaff(1));
        Assertions.assertEquals(0, sd.getAll().size());
    }

    @Test
    public void testRemove() throws SQLException {
        Assertions.assertDoesNotThrow(() -> sc.removeBooking(1));
        Assertions.assertEquals(1, bd.getAll().size());
    }

    @Test
    public void testRemoveBookingsPerDate() throws SQLException {
        Assertions.assertEquals(2, bd.getAll().size());
        Assertions.assertDoesNotThrow(() -> sc.removeBookingsPerDate(LocalDate.parse(this.date)));
        Assertions.assertEquals(0, bd.getAll().size());
    }

    @Test
    public void testRemoveBookingsPerTime() throws SQLException {
        Assertions.assertDoesNotThrow(() -> sc.removeBookingsPerTime(LocalDate.parse(this.date), LocalTime.parse(this.time), fieldDAO.get(1)));
        Assertions.assertEquals(1, bd.getAll().size());
    }
    @Test
    public void testCheckPayment() throws SQLException {
        Assertions.assertDoesNotThrow(()->sc.checkForPayment(1));
        Assertions.assertTrue(sc.checkForPayment(2));
        Assertions.assertFalse(sc.checkForPayment(1));
    }
    @Test
    public void testEnableField() throws SQLException {
        Assertions.assertDoesNotThrow(()->sc.unavailableField(fieldDAO.get(1), LocalDate.parse(this.date)));
        Assertions.assertEquals(0, bd.getAll().size());
        Assertions.assertDoesNotThrow(()->sc.availableField(fieldDAO.get(1)));
    }


}
