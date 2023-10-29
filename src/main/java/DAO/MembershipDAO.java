package main.java.DAO;

import main.java.DomainModel.Membership.*;
import main.java.DomainModel.SportsCentre;

import java.sql.*;
import java.util.ArrayList;

public class MembershipDAO {
    public Membership get(String name) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite" + "sportCentre.sqlite");
        Membership membership = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM memberships WHERE name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Membership m = new Free();
            membership = m;
            if (rs.getString("type").equals("Premium")) {
                Membership memb = new Premium(m);
                membership = memb;
            } else if (rs.getString("type").equals("GroomPack")) {
                Membership memb = new Student(m);
                membership = memb;
            }
        }
        rs.close();
        ps.close();
        connection.close();
        return membership;
    }


    public ArrayList<Membership> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        ArrayList<Membership> memberships = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM memberships");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getString("type").equals("Free")) {
                memberships.add(new Free());
            }
            if (rs.getString("type").equals("Basic")) {
                memberships.add(new Basic(new Free()));
            }
            if (rs.getString("type").equals("Premium")) {
                memberships.add(new Premium(new Basic(new Free())));
            }
            if (rs.getString("type").equals("Student")) {
                memberships.add(new Student(new Basic(new Free())));
            }
        }
        rs.close();
        ps.close();
        connection.close();
        return memberships;
    }

    public void save(Membership membership) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO memberships (name, cost, description, expirationDate, timeBeforeReserve, timeBeforeDelete) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, membership.getType());
        ps.setFloat(2, membership.getCost());
        ps.setString(3, membership.getDescription());
        ps.setInt(4, membership.getTimeBeforeReserve());
        ps.setInt(5, membership.getTimeBeforeDelete());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    public void remove(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM sportsCentres WHERE ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


}
