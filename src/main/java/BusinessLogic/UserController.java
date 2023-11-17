package main.java.BusinessLogic;

import main.java.DAO.UserDAO;
import main.java.DomainModel.Field;
import main.java.DomainModel.Membership.*;
import main.java.DomainModel.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class UserController {
private UserDAO userDAO;
private BookingController bookingController;

    public UserController(UserDAO userDAO, BookingController bookingController) {
        this.userDAO = userDAO;
        this.bookingController=bookingController;
    }

    public void subscribeMember(String fiscalCod, String name, String surname, LocalDate inscriptionDate,int membership) throws SQLException {
        Membership m=getMembership(membership);
        User user=new User(userDAO.getNextId(),fiscalCod,name,surname,inscriptionDate,m);
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


    public void addBooking(int ID, LocalDate date, int period, LocalTime time, int IDField) throws SQLException{
        bookingController.createBooking(date,period,time,IDField,ID);
    }
    public void removeBooking(int ID,int IDBooking) throws  SQLException{
        bookingController.userRemoveBooking(ID,IDBooking);
    }
    public void modifyBooking(int ID,LocalDate date,LocalTime time) throws SQLException{
        bookingController.modifyBookingDate(ID,date,time);
    }

}
