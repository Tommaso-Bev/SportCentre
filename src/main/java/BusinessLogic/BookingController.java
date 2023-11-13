package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.FieldDAO;
import main.java.DAO.UserDAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingController {
    private BookingDAO bD;
    private UserDAO uD;
    private FieldDAO fD;

    public BookingController(BookingDAO bD, UserDAO uD, FieldDAO fD) {
        this.bD = bD;
        this.uD = uD;
        this.fD = fD;
    }

    public void createBooking(int ID, LocalDate date, int period, LocalTime time, int IDField, int IDUser) throws SQLException {
        User user=uD.get(IDUser);
        if(user==null) throw new RuntimeException("The given user does not exist");
        if(bD.availableFieldAtTimeAndDate(IDField, date, time)) { throw new IllegalArgumentException("Field is already booked at this time and date");}
        if(!fD.get(IDField).getAvailability()) { throw new IllegalArgumentException("Field not available"); }
        Booking booking=new Booking(bD.getNextId(),date,period,time,user,fD.get(IDField));
        bD.save(booking);
    }

    public void removeBooking(int ID) throws SQLException {
        bD.remove(ID);
        notify();
    }

    public Booking getBooking (int ID) throws SQLException {
        return bD.get(ID);
    }

    public boolean modifyBookingDate(int ID,LocalDate date,LocalTime time){

    }


}
