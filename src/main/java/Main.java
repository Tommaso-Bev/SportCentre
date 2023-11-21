import BusinessLogic.*;
import DAO.*;
import DomainModel.*;
import DomainModel.Membership.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        //delete the tables from the past tests

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        List<String> tables = Arrays.asList("sportsCentres", "staff", "memberships", "users", "fields", "bookings");
        for (String table : tables) {
            connection.prepareStatement("DELETE FROM " + table).executeUpdate();
        }

        //initialize every controller we will eventually use
        SportsCentreDAO sportsCentreDAO=new SportsCentreDAO();
        FieldDAO fieldDAO=new FieldDAO(sportsCentreDAO);
        MembershipDAO membershipDAO=new MembershipDAO();
        UserDAO userDAO=new UserDAO(membershipDAO);
        BookingDAO bookingDAO=new BookingDAO(userDAO,fieldDAO);
        StaffDAO staffDAO=new StaffDAO(sportsCentreDAO);
        FieldController fc=new FieldController(fieldDAO);
        SportsCentreController sportsCentreController=new SportsCentreController(sportsCentreDAO,fc);
        BookingController bc=new BookingController(bookingDAO,userDAO,fieldDAO);
        StaffController staffController=new StaffController(sportsCentreController,bc,staffDAO,fieldDAO);
        UserController userController=new UserController(userDAO,bc);

        //creating the sport centres
        SportsCentre sportsCentre=new SportsCentre(1,"First centre", "First address", "1000", "no student");
        SportsCentre sportsCentre1=new SportsCentre(1,"Second centre", "Second address", "1001", "no student");
        sportsCentreController.createCentre(sportsCentre);
        sportsCentreController.createCentre(sportsCentre1);

        //adding some fields to the sport centres
        Field field=new Field(1,"tennis", 2,4,30,true, sportsCentre);
        Field field1=new Field(1,"soccer", 8,12,60,true, sportsCentre);
        Field field2=new Field(1,"tennis", 2,4,30,true, sportsCentre1);
        Field field3=new Field(1,"soccer", 8,12,60,true, sportsCentre1);
        fc.createField(field);
        fc.createField(field1);
        fc.createField(field2);
        fc.createField(field3);

        //adding the staff
        Staff staff=new Staff(1,"FSM1111","First staff", "member", LocalDate.now(), "direttore", 2200, sportsCentre);
        Staff staff1=new Staff(1,"FSM1111","First staff", "member", LocalDate.now(), "direttore", 2200, sportsCentre1);
        Staff staff2=new Staff(1,"SSM1111","Second staff", "member", LocalDate.now(), "insegnante di tennis", 1200, sportsCentre);
        Staff staff3=new Staff(1,"SSM1111","Second staff", "member", LocalDate.now(), "insegnante di calcio2", 1200, sportsCentre1);
        staffController.hireStaff(staff);
        staffController.hireStaff(staff1);
        staffController.hireStaff(staff2);
        staffController.hireStaff(staff3);

        //to create some users
        Membership membership=new Free();
        Membership membership1=new Basic(membership);
        Membership membership2=new Premium(membership1);
        User user1=new User(1,"SJCB1111R","Sir Joe","Cibechins ", LocalDate.now(),membership);
        User user2=new User(1,"MRHK2222L","Myr Cows","Harsh keys ", LocalDate.now(),membership1);
        User user3=new User(1,"MRNL3333K","Mar Cows","Nightlings ", LocalDate.now(),membership2);
        userController.subscribeMember(user1);
        userController.subscribeMember(user2);
        userController.subscribeMember(user3);

        //setup for the membershipDAO
        membershipDAO.save(membership);
        membershipDAO.save(membership1);
        membershipDAO.save(membership2);
        membershipDAO.save(new Student(membership2));

        //making some bookings
        Booking booking=new Booking(1,LocalDate.now(),2, LocalTime.now(), user1,field);
        Booking booking1=new Booking(1,LocalDate.now(),1, LocalTime.now(), user1,field1);
        Booking booking2=new Booking(1,LocalDate.now(),3, LocalTime.now(), user2,field2);
        Booking booking3=new Booking(1,LocalDate.now(),2, LocalTime.now(), user3,field3);
        userController.addBooking(booking);
        userController.addBooking(booking1);
        userController.addBooking(booking2);
        userController.addBooking(booking3);

        //testing the methods of the staff controller class
        staffController.removeBooking(1);
        staffController.removeBookingsPerDate(LocalDate.now());
        staffController.unavailableField(field,LocalDate.now());
        try {
            userController.addBooking(new Booking(1, LocalDate.now(), 2, LocalTime.now(), user1, fieldDAO.get(1)));
        } catch (Exception e){
            System.err.print(e.getMessage());
        }
        staffController.availableField(fieldDAO.get(1));
        System.out.print("\n");
        try {
            userController.addBooking(new Booking(1, LocalDate.now(), 2, LocalTime.now(), user1, fieldDAO.get(1)));
        } catch (Exception e){
            System.err.print(e.getMessage());
        }
        try {
            userController.addBooking(new Booking(1,LocalDate.now().plusDays(10),2,LocalTime.now(), user1, fieldDAO.get(1)));
        } catch (Exception e){
            System.err.print(e.getMessage());
        }
    }
}
