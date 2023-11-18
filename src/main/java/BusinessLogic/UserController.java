package BusinessLogic;

import DAO.UserDAO;
import DomainModel.Booking;
import DomainModel.Membership.*;
import DomainModel.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class UserController {
private UserDAO userDAO;
private BookingController bookingController;

    public UserController(UserDAO userDAO, BookingController bookingController) {
        this.userDAO = userDAO;
        this.bookingController=bookingController;
    }

    public void subscribeMember(User user) throws SQLException {
        user.setID(userDAO.getNextId());
        userDAO.save(user);
    }

    public Membership getMembership(int membershipType){
        Membership m=new Free();
        if (membershipType == 1){
            m=new Basic(m);
        }
        else if (membershipType == 2){
            m=new Student(m);
        } else if (membershipType == 3) {
            m=new Premium(m);
        }
        return m;
    }
    public void unsubscribeMember(int ID) throws SQLException {
        this.userDAO.remove(ID);
    }
    public User getMember(int ID) throws SQLException {
        return this.userDAO.get(ID);
    }
    public void changeMembership(int ID,int membership) throws SQLException {
        Membership m=getMembership(membership);
        User user=userDAO.get(ID);
        String[] modify={user.getCodFisc(),user.getName(),user.getSurname(),user.getInscriptionDate(),m.getType()};
        userDAO.modify(user,modify);
    }


    public void addBooking(Booking booking) throws SQLException{
        bookingController.createBooking(booking);
    }
    public void removeBooking(int ID,int IDBooking) throws  SQLException{
        bookingController.userRemoveBooking(ID,IDBooking);
    }
    public void modifyBooking(int ID,LocalDate date,LocalTime time) throws SQLException{
        bookingController.modifyBookingDate(ID,date,time);
    }

}
