package main.java.DAO;

import main.java.DomainModel.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDAO implements DAO<User> {
    MembershipDAO membershipDAO;
    public UserDAO(MembershipDAO membershipDAO){
        this.membershipDAO=membershipDAO;
    }

    @Override
    public User get(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        User user=null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM users WHERE ID= ?");
        prepStat.setInt(1, id);
        ResultSet rs=prepStat.executeQuery();
        if (rs.next()){
            user= new User(id, rs.getString("codFisc"), rs.getString("firstName"), rs.getString("surname"), LocalDate.parse(rs.getString("inscriptionDate")) ,membershipDAO.get(rs.getString("membershipsName")) );
        }
        rs.close();
        prepStat.close();
        connection.close();
        return user;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            users.add(new User(rs.getInt("ID"), rs.getString("codFisc"), rs.getString("firstName"), rs.getString("surname"),LocalDate.parse(rs.getString("inscriptionDate")) ,membershipDAO.get(rs.getString("membershipsName"))));
        }
        rs.close();
        ps.close();
        connection.close();
        return users;
    }


    @Override
    public void save(User user) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (ID,codFisc,firstName,surname,inscriptionDate,membershipsName) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1,user.getID());
        ps.setString(2, user.getCodFisc());
        ps.setString(3, user.getName());
        ps.setString(4, user.getSurname());
        ps.setString(5, user.getInscriptionDate());
        ps.setString(6, user.getMembershipName());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    @Override
    public void modify(User user, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET codFisc = ?, firstName = ?, surname = ?,inscriptionDate = ?,membershipsName = ?   WHERE ID = ?");
        ps.setString(1, args[0]);
        ps.setString(2, args[1]);
        ps.setString(3, args[2]);
        ps.setString(4, args[3]);
        ps.setString(5,args[4]);
        ps.setInt(6, user.getID());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public int getNextId() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        String query = "SELECT MAX(ID) FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id; //TODO check if it works
        if(rs.next()){
            id = rs.getInt(1) + 1;
        }else {
            id=1;
        }

        rs.close();
        statement.close();
        connection.close();
        return id;
    }
    public int getDiscount(String membershipName) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        int discount=0;
        PreparedStatement prepStat=connection.prepareStatement("SELECT discount FROM memberships WHERE name= ?");
        prepStat.setString(1, membershipName);
        ResultSet rs=prepStat.executeQuery();
        if (rs.next()){
            discount= rs.getInt("discount");
        }
        rs.close();
        prepStat.close();
        connection.close();
        return discount;
    }



}
