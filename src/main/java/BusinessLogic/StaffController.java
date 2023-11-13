package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.StaffDAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;
import main.java.DomainModel.Staff;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class StaffController {
    public StaffController(SportsCentreController sc, BookingController bc, StaffDAO sd) {
        this.sc = sc;
        this.bc = bc;
        this.sd = sd;
    }

    public void hireStaff(String codFisc, String firstName, String surname, LocalDate hiringDate, String task, int salary, SportsCentre sportsCentre) throws SQLException {
        Staff staff = new Staff(sd.getNextId(), codFisc, firstName, surname, hiringDate, task, salary, sportsCentre);
        sd.save(staff);
    }

    public void fireStaff(int id) throws SQLException {
        sd.remove(id);
    }

    public Staff getStaff(int id) throws SQLException {
        return sd.get(id);
    }

    public String removeBooking(int id) throws SQLException {
        Random choice=new Random();
        switch (choice.nextInt(0,1)){
            default:
                bc.removeBooking(id);
            case 0:
                return "There's been a problem with your booking so it has to be rearranged";
            case 1:
                return "There's been an unexpected problem with your field, if you can, change it, the booking has been canceled";
        }
    }

    public String removeBookingsPerDate(Date date, Field field) throws SQLException {
        Random choice=new Random();
        ArrayList<Booking> toRemove=bc.getBookingsForDate(date);
        for (Booking booking : toRemove) bc.removeBooking(booking.getID());
        return switch (choice.nextInt(0, 1)) {
            case 0 -> "Because of technical reasons the date selected cannot be used";
            case 1 -> "Because of a problem with the fields in this date the faculty cannot be used";
            default -> null;
        };
    }

    public String removeBookingsPerTime(Date date, LocalTime time, Field field) throws SQLException {
        Random choice=new Random();
        ArrayList<Booking> toRemove=bc.getBookingsForDate(date);
        for (Booking booking : toRemove)
            if (booking.getTime() == time & booking.getField()==field)
                bc.removeBooking(booking.getID());
        return switch (choice.nextInt(0, 1)) {
            case 0 -> "Because of technical reasons the date selected cannot be used";
            case 1 -> "Because of a problem with the field in this date and time the faculty cannot be used";
            default -> null;
        };
    }

    public boolean checkForPayment(int id) throws SQLException {
        return bc.getBooking(id).isPayed();
    }
    private SportsCentreController sc;
    private BookingController bc;
    private StaffDAO sd;

}
