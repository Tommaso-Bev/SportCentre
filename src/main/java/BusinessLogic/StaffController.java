package main.java.BusinessLogic;

import main.java.DAO.FieldDAO;
import main.java.DAO.StaffDAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;
import main.java.DomainModel.Staff;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class StaffController {
    public StaffController(SportsCentreController sc, BookingController bc, StaffDAO sd, FieldDAO fd) {
        this.sc = sc;
        this.bc = bc;
        this.sd = sd;
        this.fd=fd;
    }

    public void hireStaff(Staff staff) throws SQLException {
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
        switch (choice.nextInt(1)){
            default:
                bc.removeBooking(id);
            case 0:
                return "There's been a problem with your booking so it has to be rearranged";
            case 1:
                return "There's been an unexpected problem with your field, if you can, change it, the booking has been canceled";
        }
    }

    public String removeBookingsPerDate(LocalDate date, Field field) throws SQLException {
        Random choice=new Random();
        ArrayList<Booking> toRemove=bc.getBookingsForDate(date);
        for (Booking booking : toRemove) bc.removeBooking(booking.getID());
        return switch (choice.nextInt(1)) {
            case 0 -> "Because of technical reasons the date selected cannot be used";
            case 1 -> "Because of a problem with the fields in this date the faculty cannot be used";
            default -> null;
        };
    }

    public String removeBookingsPerTime(LocalDate date, LocalTime time, Field field) throws SQLException {
        Random choice=new Random();
        ArrayList<Booking> toRemove=bc.getBookingsForDate(date);
        for (Booking booking : toRemove)
            if (booking.getTime() == time & booking.getField()==field)
                bc.removeBooking(booking.getID());
        return switch (choice.nextInt(1)) {
            case 0 -> "Because of technical reasons the date selected cannot be used";
            case 1 -> "Because of a problem with the field in this date and time the faculty cannot be used";
            default -> null;
        };
    }

    public boolean checkForPayment(int id) throws SQLException {
        return bc.getBooking(id).isPayed();
    }

    public void unavailableField(Field field, LocalDate date) throws SQLException
    {
        fd.habilitateField(field.getId(),false);
        removeBookingsPerDate(date,field);
    }
    public void availableField(Field field) throws SQLException
    {
        fd.habilitateField(field.getId(),true);
    }

    private SportsCentreController sc;
    private BookingController bc;
    private StaffDAO sd;
    private FieldDAO fd;

}
