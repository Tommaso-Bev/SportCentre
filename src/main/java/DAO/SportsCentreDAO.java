package main.java.DAO;
import main.java.DomainModel.SportsCentre;

import java.sql.*;
import java.util.ArrayList;


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
            sportsCentres.add(new SportsCentre(rs.getInt("ID"),rs.getString("name"),rs.getString("address"),rs.getString("CAP"),rs.getString("type")));
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
        ps.setInt(1,sportsCentre.getId());
        ps.setString(2, sportsCentre.getName());
        ps.setString(3, sportsCentre.getAddress());
        ps.setString(4, sportsCentre.getCAP());
        ps.setString(5, sportsCentre.getType());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    @Override
    public void modify(SportsCentre sportsCentre, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("UPDATE sportsCentres SET name = ?, address = ?, CAP = ?, type = ? WHERE ID = ?");
        ps.setString(1, args[0]);
        ps.setString(2, args[1]);
        ps.setString(3, args[2]);
        ps.setString(4, args[3]);
        ps.setInt(5, sportsCentre.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM sportsCentres WHERE ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public int getNextId() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        String query = "SELECT MAX(ID) FROM sportsCentres";
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

}
