package main.java.DAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.SportsCentre;
import main.java.DomainModel.Booking;

import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
public class BookingDAO implements DAO<Booking>{
    SportsCentreDAO sportsCentreDAO;

    public BookingDAO(SportsCentreDAO sportsCentreDAO ){this.sportsCentreDAO=sportsCentreDAO;}

    @Override
    public Booking get(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        Booking booking= null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM bookings WHERE ID=?");
        prepStat.setInt(1,id);
        ResultSet resultSet=prepStat.executeQuery();
        if(resultSet.next()){
            booking=new Booking(id, LocalDate.parse(resultSet.getString("date")),resultSet.getInt("period"));
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
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("data")),rs.getInt("period")));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }

    @Override
    public void save(Booking booking) throws SQLException {

    }

    @Override
    public void modify(Booking booking, String[] args) throws SQLException {

    }

    @Override
    public void remove(int id) throws SQLException {

    }

    @Override
    public int getNextId() throws SQLException {
        return 0;
    }
}
