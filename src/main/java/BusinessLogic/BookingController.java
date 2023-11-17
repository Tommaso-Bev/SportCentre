package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.FieldDAO;
import main.java.DAO.UserDAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.Field;
import main.java.DomainModel.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Objects;

public class BookingController implements Subject{
    private BookingDAO bD;
    private UserDAO uD;
    private FieldDAO fD;

    public BookingController(BookingDAO bD, UserDAO uD, FieldDAO fD) {
        this.bD = bD;
        this.uD = uD;
        this.fD = fD;
    }

    public void createBooking(LocalDate date, int period, LocalTime time, int IDField, int IDUser) throws SQLException {
        User user=uD.get(IDUser);
        Field field=fD.get(IDField);
        if(user==null) throw new RuntimeException("The given user does not exist");
        if(!user.canBook(date)){ throw new IllegalArgumentException("User membership does not allow the book");}
        if(bD.availableFieldAtTimeAndDate(IDField, date, time)) { throw new IllegalArgumentException("Field is already booked at this time and date");}
        if(!field.getAvailability()) { throw new IllegalArgumentException("Field not available"); }
        Booking booking=new Booking(bD.getNextId(),date,period,time,user,field);
        bD.save(booking);
        notifyobservers(IDUser,"the booking was successful, you have to pay: "+ getPrice(period,user,field)+"$");
    }

    public float getPrice(int period, User user, Field field ) throws SQLException {
        float price=0;
        float tot=(field.getFineph()*period);
        if(Objects.equals(field.getSportCentre().getType(), "STUDENT")){
            price=tot-((tot*uD.getDiscount(user.getMembershipName()))/100);
        }else {
            if(Objects.equals(user.getMembershipName(), "Premium")){
                price=tot-((tot*uD.getDiscount(user.getMembershipName()))/100);
            }
            price=tot;
        }
        return price;
    }
    public void removeBooking(int ID) throws SQLException {
        bD.remove(ID);
        notifyobservers(bD.get(ID).getUser().getID(),"the booking was cancelled");
    }

    public void userRemoveBooking(int IDUser, int ID) throws SQLException {
        User user=uD.get(IDUser);
        LocalDate now=LocalDate.now();
        if(!user.canDeleteModifyBook(now)){ throw new IllegalArgumentException("User membership does not allow to delete the book");}
        bD.remove(ID);
        notifyobservers(IDUser," the cancellation of booking was successful ");
    }

    public Booking getBooking (int ID) throws SQLException {
        return bD.get(ID);
    }

    public boolean modifyBookingDate(int ID,LocalDate date,LocalTime time) throws SQLException {
        Booking tmpBooking=bD.get(ID);
        if(!tmpBooking.getUser().canDeleteModifyBook(date)){ throw new IllegalArgumentException("User membership does not allow the book");}
        userRemoveBooking(tmpBooking.getUser().getID(),ID);
        for (int i = 0; i < tmpBooking.getPeriod() ; i++) {
            if(bD.availableFieldAtTimeAndDate(tmpBooking.getField().getId(), date, time.plusHours(i))) {bD.save(tmpBooking); throw new IllegalArgumentException("Field is already booked at this time and date");}
            if(!fD.get(tmpBooking.getField().getId()).getAvailability()) {bD.save(tmpBooking); throw new IllegalArgumentException("Field not available");}
        }
        bD.save(new Booking(tmpBooking.getID(), date, tmpBooking.getPeriod(), time,tmpBooking.getUser(), tmpBooking.getField() ));
        notifyobservers(bD.get(ID).getUser().getID(),"The change was successful ");
        return true;
    }


    public ArrayList<Booking> getBookingsForDate(LocalDate date) throws SQLException {
        return bD.getBookingsForDate(date);
    }

    @Override
    public void notifyobservers(int IDUser,String message)throws  SQLException {
        User observer= uD.get(IDUser);
        observer.update(message);
    }
}
