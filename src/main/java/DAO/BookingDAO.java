package main.java.DAO;
import main.java.DomainModel.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class BookingDAO implements DAO<Booking>{
    SportsCentreDAO sportsCentreDAO;
    UserDAO userDAO;
    FieldDAO fieldDAO;
    public BookingDAO(SportsCentreDAO sportsCentreDAO, UserDAO usd, FieldDAO fieldDAO ){
        this.sportsCentreDAO=sportsCentreDAO;
        this.userDAO=usd;
        this.fieldDAO=fieldDAO;
    }

    private int getBookingCountById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        int rowCount = 0;

        String query = "SELECT COUNT(*) as count FROM bookings WHERE ID=?";
        PreparedStatement prepStat = connection.prepareStatement(query);
        prepStat.setInt(1, id);

        ResultSet resultSet = prepStat.executeQuery();
        if (resultSet.next()) {
            rowCount = resultSet.getInt("count");
        }

        resultSet.close();
        prepStat.close();
        connection.close();
        return rowCount;
    }


    @Override
    public Booking get(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        Booking booking= null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM bookings WHERE ID=? ORDER BY time ASC");
        prepStat.setInt(1,id);
        ResultSet resultSet=prepStat.executeQuery();
        if(resultSet.next()){
            booking=new Booking(id, LocalDate.parse(resultSet.getString("date")),getBookingCountById(id), LocalTime.parse(resultSet.getString("time")),userDAO.get(resultSet.getInt("users")),fieldDAO.get(resultSet.getInt("field")));
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
        PreparedStatement ps= connection.prepareStatement("SELECT DISTINCT ID FROM bookings");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            bookings.add(get(rs.getInt("ID")));

        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }

    @Override
    public void save(Booking booking) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        float count =0;
        while (count<booking.getPeriod()) {
            PreparedStatement ps= connection.prepareStatement("INSERT INTO bookings(ID,date,time,users,field)VALUES (?,?,?,?,?)");
            ps.setInt(1,booking.getID());
            ps.setString(2, booking.getDate().toString());
            ps.setString(3,booking.getTime().plusHours((long) count).toString());
            ps.setInt(4,booking.getUser().getID());
            ps.setInt(5,booking.getField().getId());
            ps.executeQuery();
            ps.close();
            count++;
        }

        connection.close();
    }

    @Override
    public void modify(Booking booking,LocalTime time, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("UPDATE bookings SET date=?,time=?, users=?, field=? WHERE ID=? AND time=?");
        ps.setString(1,args[0]);
        ps.setString(2,args[2]);
        ps.setInt(3,Integer.parseInt(args[3]));
        ps.setInt(4,Integer.parseInt(args[4]));
        ps.setInt(5,booking.getID());
        ps.setString(6,time.toString());
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
    public boolean availableFieldAtTimeAndDate(int fieldID,LocalDate date,LocalTime time) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE field=? AND date=? AND time=?");
        ps.setInt(1,fieldID);
        ps.setString(2,date.toString());
        ps.setString(3,time.toString());
        ResultSet rs=ps.executeQuery();
        boolean result =rs.next();
        ps.close();
        connection.close();
        return result;
    }
    public ArrayList<Booking> getBookingsForMember(int memberID) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE users=? ");
        ps.setInt(1,memberID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time")),userDAO.get(rs.getInt("users")),fieldDAO.get(rs.getInt("field"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForField(int fieldID) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE field=? ");
        ps.setInt(1,fieldID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time")),userDAO.get(rs.getInt("users")),fieldDAO.get(rs.getInt("field"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForDate(Date date) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE date=? ");
        ps.setString(1,date.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time")),userDAO.get(rs.getInt("users")),fieldDAO.get(rs.getInt("field"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForDateAndTime(LocalDate ld,LocalTime time) throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT * FROM bookings WHERE date=? AND time=?  ");
        ps.setString(1,ld.toString());
        ps.setString(2,time.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(new Booking(rs.getInt("ID"),LocalDate.parse(rs.getString("date")),rs.getInt("period"),LocalTime.parse(rs.getString("time")),userDAO.get(rs.getInt("users")),fieldDAO.get(rs.getInt("field"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }



}

