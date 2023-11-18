package DAO;
import DomainModel.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class BookingDAO implements DAO<Booking>{
    UserDAO userDAO;
    FieldDAO fieldDAO;
    public BookingDAO(UserDAO usd, FieldDAO fieldDAO ){
        this.userDAO=usd;
        this.fieldDAO=fieldDAO;
    }

    private int getPeriod(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
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
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        Booking booking= null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM bookings WHERE ID=? ORDER BY time ASC");
        prepStat.setInt(1,id);
        ResultSet resultSet=prepStat.executeQuery();
        if(resultSet.next()){
            booking=new Booking(id, LocalDate.parse(resultSet.getString("date")),getPeriod(id), LocalTime.parse(resultSet.getString("time")),userDAO.get(resultSet.getInt("users")),fieldDAO.get(resultSet.getInt("field")));
            if (resultSet.getInt("payed")==0)
                booking.setPayed(false);
            else
                booking.setPayed(true);
        }

        resultSet.close();
        prepStat.close();
        connection.close();
        return booking;

    }

    @Override
    public ArrayList<Booking> getAll() throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
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
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        float count =0;
        Random random=new Random();
        while (count<booking.getPeriod()) {
            PreparedStatement ps= connection.prepareStatement("INSERT INTO bookings(ID,date,time,payed,users,field)VALUES (?,?,?,?,?,?)");
            ps.setInt(1,booking.getID());
            ps.setString(2, booking.getDate().toString());
            ps.setString(3,booking.getTime().plusHours((long) count).toString());
            ps.setInt(4,random.nextInt(1));
            ps.setInt(5,booking.getUser().getID());
            ps.setInt(6,booking.getField().getId());
            ps.executeUpdate();
            ps.close();
            count++;
        }

        connection.close();
    }

    @Override
    public void modify(Booking booking, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("UPDATE bookings SET date=?,time=?, users=?, field=? WHERE ID=? AND time=?");
        ps.setString(1,args[0]);
        ps.setString(2,args[1]);
        ps.setInt(3,Integer.parseInt(args[2]));
        ps.setInt(4,Integer.parseInt(args[3]));
        ps.setInt(5,booking.getID());
        ps.setString(6,booking.getTime().toString());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement st = connection.prepareStatement("SELECT ID FROM bookings WHERE ID = ?");
        st.setInt(1, id);
        ResultSet resultSet = st.executeQuery();

        // Verify if booking with this id exist
        if (!resultSet.next()) {
            throw new SQLException("Booking not found: " + id);
        }

        // Proceed to delete the booking
        PreparedStatement ps= connection.prepareStatement("DELETE FROM bookings WHERE ID=?");
        ps.setInt(1,id);
        ps.executeUpdate();
        ps.close();
        connection.close();

    }

    @Override
    public int getNextId() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
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
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
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
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT DISTINCT ID FROM bookings WHERE users=? ");
        ps.setInt(1,memberID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(get(rs.getInt("ID")));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForField(int fieldID) throws SQLException{
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT DISTINCT ID FROM bookings WHERE field=? ");
        ps.setInt(1,fieldID);
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(get(rs.getInt("ID")));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForDate(LocalDate date) throws SQLException{
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT DISTINCT ID FROM bookings WHERE date=? ");
        ps.setString(1,date.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(get(rs.getInt("ID")));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }
    public ArrayList<Booking> getBookingsForDateAndTime(LocalDate ld,LocalTime time) throws SQLException{
        Connection connection= DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps= connection.prepareStatement("SELECT DISTINCT ID FROM bookings WHERE date=? AND time=?  ");
        ps.setString(1,ld.toString());
        ps.setString(2,time.toString());
        ArrayList<Booking> bookings= new ArrayList<>();
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
            bookings.add(get(rs.getInt("ID")));
        }
        rs.close();
        ps.close();
        connection.close();
        return bookings;
    }



}

