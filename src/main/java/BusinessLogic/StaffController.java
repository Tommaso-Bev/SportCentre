package BusinessLogic;

import DAO.FieldDAO;
import DAO.StaffDAO;
import DomainModel.Booking;
import DomainModel.Field;
import DomainModel.Staff;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class StaffController {
    public StaffController(SportsCentreController sc, BookingController bc, StaffDAO sd, FieldDAO fd) {
        this.sc = sc;
        this.bc = bc;
        this.sd = sd;
        this.fd = fd;
    }

    public void hireStaff(Staff staff) throws SQLException {
        staff.setID(sd.getNextId());
        sd.save(staff);
    }

    public void fireStaff(int id) throws SQLException {
        sd.remove(id);
    }

    public Staff getStaff(int id) throws SQLException {
        return sd.get(id);
    }

    public String removeBooking(int id) throws SQLException {
        bc.removeBooking(id);
        return "a";
    }

    public String removeBookingsPerDate(LocalDate date, Field field) throws SQLException {
        Random choice = new Random();
        ArrayList<Booking> toRemove = bc.getBookingsForDate(date);
        for (Booking booking : toRemove)
            bc.removeBooking(booking.getID());
        return "a";
    }

    public String removeBookingsPerTime(LocalDate date, LocalTime time, Field field) throws SQLException {
        Random choice = new Random();
        ArrayList<Booking> toRemove = bc.getBookingsForDate(date);
        for (Booking booking : toRemove)
            if (booking.getTime() == time & booking.getField() == field)
                bc.removeBooking(booking.getID());

        return "a";
    }

    public boolean checkForPayment(int id) throws SQLException {
        return bc.getBooking(id).isPayed();
    }

    public void unavailableField(Field field, LocalDate date) throws SQLException {
        fd.habilitateField(field.getId(), false);
        removeBookingsPerDate(date, field);
    }

    public void availableField(Field field) throws SQLException {
        fd.habilitateField(field.getId(), true);
    }


    private SportsCentreController sc;
    private BookingController bc;
    private StaffDAO sd;
    private FieldDAO fd;

}
