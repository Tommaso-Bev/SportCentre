package main.java.BusinessLogic;

import main.java.DAO.UserDAO;
import main.java.DomainModel.Membership.*;
import main.java.DomainModel.User;

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

    public void subscribeMember(String fiscalCod, String name, String surname, LocalDate inscriptionDate,int membership) throws SQLException {
        Membership m=new Free();
        if (membership == 1){
            m=new Basic(m);
        }
        else if (membership == 2){
            m=new Student(m);
        } else if (membership == 3) {
            m=new Premium(m);
        }
        User user=new User(userDAO.getNextId(),fiscalCod,name,surname,inscriptionDate,m);
        userDAO.save(user);

    }
    public void unsubscribeMember(int ID) throws SQLException {
        this.userDAO.remove(ID);
    }
    public User getMember(int ID) throws SQLException {
        return this.userDAO.get(ID);
    }
    public void changeMembership(int ID,int membership) throws SQLException {
        Membership m=new Free();
        if (membership == 1){
            m=new Basic(m);
        }
        else if (membership == 2){
            m=new Student(m);
        } else if (membership == 3) {
            m=new Premium(m);
        }
        User user=userDAO.get(ID);
        String[] modify={user.getCodFisc(),user.getName(),user.getSurname(),user.getInscriptionDate(),m.getType()};
        userDAO.modify(user,modify);
    }


    public void addBooking(int ID, LocalDate date, int period, LocalTime time, int IDField) throws SQLException{
        bookingController.createBooking(date,period,time,IDField,ID);
    }
    public void removeBooking(int IDuser,int ID) throws  SQLException{
        bookingController.userRemoveBooking(IDuser,ID);
    }
    public void modifybooking(int ID,LocalDate date,LocalTime time) throws SQLException{
        bookingController.modifyBookingDate(ID,date,time);
    }

}
