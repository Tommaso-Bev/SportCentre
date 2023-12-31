package DAO;

import DomainModel.Membership.*;
import DomainModel.SportsCentre;

import java.sql.*;
import java.util.ArrayList;

public class MembershipDAO {
    public Membership get(String name) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        Membership membership = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM memberships WHERE name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("name").equals("Free")) {
                membership=new Free();
            }
            if (rs.getString("name").equals("Basic")) {
                membership=new Basic(new Free());
            }
            if (rs.getString("name").equals("Premium")) {
                membership=new Premium(new Basic(new Free()));
            }
            if (rs.getString("name").equals("Student")) {
                membership=new Student(new Basic(new Free()));
            }
        }
        rs.close();
        ps.close();
        connection.close();
        return membership;
    }


    public ArrayList<Membership> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        ArrayList<Membership> memberships = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM memberships");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getString("name").equals("Free")) {
                memberships.add(new Free());
            }
            if (rs.getString("name").equals("Basic")) {
                memberships.add(new Basic(new Free()));
            }
            if (rs.getString("name").equals("Premium")) {
                memberships.add(new Premium(new Basic(new Free())));
            }
            if (rs.getString("name").equals("Student")) {
                memberships.add(new Student(new Basic(new Free())));
            }
        }
        rs.close();
        ps.close();
        connection.close();
        return memberships;
    }

    public void save(Membership membership) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO memberships (name, cost, description, timeBeforeReserve, timeBeforeDelete, discount) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, membership.getType());
        ps.setFloat(2, membership.getCost());
        ps.setString(3, membership.getDescription());
        ps.setInt(4, membership.getTimeBeforeReserve());
        ps.setInt(5, membership.getTimeBeforeDelete());
        ps.setInt(6, membership.getDiscount());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    public void remove(String name) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM memberships WHERE name = ?");
        ps.setString(1, name);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


}
