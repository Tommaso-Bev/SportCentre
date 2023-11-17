package DAO;
import main.java.DAO.*;
import main.java.DomainModel.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingDAOTest {

    private SportsCentreDAO sportsCentreDAO=new SportsCentreDAO();
    private MembershipDAO membershipDAO=new MembershipDAO();
    private UserDAO userDAO=new UserDAO(membershipDAO);
    private FieldDAO fieldDAO=new FieldDAO(sportsCentreDAO);
    private BookingDAO bookingDAO;

    @BeforeAll
    public static void setUpBeforeAll() throws IOException, SQLException {
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
        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (1, 'SportCentre1', 'address1','CAP1','NONE')").executeUpdate();
        connection.prepareStatement("INSERT INTO sportsCentres (id, name, address, CAP, type) VALUES (2, 'SportCentre1', 'address2','CAP2','STUDENT' )").executeUpdate();

        connection.prepareStatement("INSERT INTO fields (id, name, available) VALUES (1, 'name1', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, name, available) VALUES (2, 'name2', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO fields (id, name, available) VALUES (2, 'name2', true)").executeUpdate();

        this.data = LocalDate.now().toString();
        this.ora = LocalTime.now().truncatedTo(ChronoUnit.HOURS).toString();

        connection.prepareStatement("INSERT INTO memberships (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO memberships (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO memberships (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO memberships (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();

        connection.prepareStatement("INSERT INTO users (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO users (fiscalCode, firstName, lastName, ) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();

        connection.prepareStatement("INSERT INTO bookings (id, , , date, time) VALUES (1, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now() + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (id, , , date, time) VALUES (2, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now().plusHours(1) + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (id, , , date, time) VALUES (3, 1, 'AAAAAA11', '" + this.data + "', '" + this.ora + "')").executeUpdate();


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
        // Test per il metodo save(Booking booking)
        // Crea una nuova prenotazione da salvare nel database
        Booking newBooking = new Booking(/* specifica i parametri della prenotazione */);
        bookingDAO.save(newBooking);
        // Effettua le asserzioni per verificare che il salvataggio sia avvenuto correttamente
        // Assertions.assertTrue(condition);
    }

    // Altri test per i vari metodi della classe BookingDAO...

}
