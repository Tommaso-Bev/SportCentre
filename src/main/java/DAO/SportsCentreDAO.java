package main.java.DAO;
import main.java.DomainModel.SportsCentre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportsCentreDAO implements DAO<SportsCentre> {

    @Override
    public SportsCentre get(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite"+"sportCentre.sqlite");
        SportsCentre sportsCentre = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM sportsCentres WHERE ID = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            sportsCentre = new SportsCentre( id,rs.getString("name"),rs.getString("address"),rs.getString("CAP"),rs.getString("type"));
        }
        rs.close();
        ps.close();
        connection.close();
        return sportsCentre;
    }


    @Override
    public ArrayList<SportsCentre> getAll() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        ArrayList<SportsCentre> sportsCentres = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM sportsCentres");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            sportsCentres.add(new SportsCentre(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("CAP"),rs.getString("type")));
        }
        rs.close();
        ps.close();
        connection.close();
        return sportsCentres;
    }

    @Override
    public void save(SportsCentre sportsCentre) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO sportsCentres (ID, name, address, CAP, type) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1,getNextId());
        ps.setString(2, sportsCentre.getName());
        ps.setString(3, sportsCentre.getAddress());
        ps.setString(4, sportsCentre.getCAP());
        ps.setString(5, sportsCentre.getType());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void modify(SportsCentre sportsCentre, SportsCentre[] args) {

    }

    @Override
    public void remove(SportsCentre sportsCentre) {

    }

    @Override
    public int assignID() {
        return 0;
    }
}
