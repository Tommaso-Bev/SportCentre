package DAO;
import main.java.DAO.*;
import main.java.DomainModel.*;
import main.java.DomainModel.Membership.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite").createStatement();
        stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();
    }

    @BeforeEach
    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
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
    public void testGetBookingById() throws SQLException {
        // Test per il metodo get(int id)
        int id = 1; // Sostituire con l'ID esistente nel tuo database
        Booking booking = bookingDAO.get(id);
        // Effettua le asserzioni per verificare che il risultato sia corretto
        // Assertions.assertNotNull(booking);
        // Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testGetAllBookings() throws SQLException {
        // Test per il metodo getAll()
        ArrayList<Booking> bookings = bookingDAO.getAll();
        // Effettua le asserzioni per verificare che il risultato sia corretto
        // Assertions.assertNotNull(bookings);
        // Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testSaveBooking() throws SQLException {
        User user=userDAO.get(2);
        Field field=fieldDAO.get(2);
        Booking booking = new Booking(4, LocalDate.now(), 3, LocalTime.now().plusHours(2), user, field);
        Assertions.assertDoesNotThrow(() -> bookingDAO.save(booking));
        Assertions.assertEquals(4, bookingDAO.getAll().size());
    }

    // Altri test per i vari metodi della classe BookingDAO...

}
