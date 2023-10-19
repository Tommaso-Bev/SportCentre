package main.java.DAO;
import main.java.DomainModel.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class BookingDAO implements DAO<Booking>{
    SportsCentreDAO sportsCentreDAO;
    UserDAO userDAO;
    public BookingDAO(SportsCentreDAO sportsCentreDAO, UserDAO usd ){
        this.sportsCentreDAO=sportsCentreDAO;
        this.userDAO=usd;
    }


    @Override
    public Booking get(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        Booking booking= null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM bookings WHERE ID=?");
        prepStat.setInt(1,id);
        ResultSet resultSet=prepStat.executeQuery();
        if(resultSet.next()){
            booking=new Booking(id, LocalDate.parse(resultSet.getString("date")),resultSet.getInt("period"), LocalTime.parse(resultSet.getString("time")));
        }
        resultSet.close();
        prepStat.close();
        connection.close();
        return booking;
    }

    @Override
    public ArrayList<Booking> getAll() throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        ArrayList<Booking> bookings= new ArrayList<>();
        PreparedStatement ps= connection.prepareStatement("SELECT*FROM bookings");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("data")),rs.getInt("period"),LocalTime.parse(rs.getString("time"))));

        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }

    @Override
    public void save(Booking booking) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("INSERT INTO bookings(ID,date,period,time)VALUES (?,?,?,?)");
        ps.setInt(1,booking.getID());
        ps.setString(2, booking.getDate().toString());
        ps.setInt(3,booking.getPeriod());
        ps.setString(4,booking.getTime().toString());
        ps.executeQuery();
        ps.close();
        connection.close();
    }

    @Override
    public void modify(Booking booking, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("UPDATE bookings SET date=?,period=?,time=? WHERE ID=?");
        ps.setString(1,args[0]);
        ps.setInt(2,Integer.parseInt(args[1]));
        ps.setString(3,args[2]);
        ps.setInt(4,booking.getID());
        ps.executeQuery();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("DELETE FROM bookings WHERE ID=?");
        ps.setInt(1,id);
        ps.executeQuery();
        ps.close();
        connection.close();

    }

    @Override
    public int getNextId() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        String query="SELECT MAX(ID) FROM bookings";
        PreparedStatement ps= connection.prepareStatement(query);
        ResultSet rs= ps.executeQuery();
        int id;
        if(rs.next()){
            id=rs.getInt(1)+1;
        }else
            id=1;
        rs.close();
        ps.close();
        connection.close();
        return id;
    }
    public boolean availableField(int fieldID) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE field=?");
        ps.setInt(1,fieldID);
        ResultSet rs=ps.executeQuery();
        boolean av =false;
        if(rs.next()){
            av=rs.getBoolean(1);
        }
        ps.close();
        connection.close();
        return av;
    }
    public ArrayList<Booking> getBookingForMember(int meberID) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE users=? ");
        ps.setInt(1,meberID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingForField(int fieldID) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE field=? ");
        ps.setInt(1,fieldID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingForDate(Date date) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE date=? ");
        ps.setString(1,date.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingForDateAndTime(LocalDate ld,LocalTime time) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE date=? AND time=?  ");
        ps.setString(1,ld.toString());
        ps.setString(2,time.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }



}

