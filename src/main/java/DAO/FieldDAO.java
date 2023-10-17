package main.java.DAO;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;

import java.sql.*;
import java.util.ArrayList;

public class FieldDAO implements DAO<Field>{
    @Override
    public Field get(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.sqlite");
        Field field=null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM fields WHERE ID= ?");
        prepStat.setInt(1, id);
        ResultSet resSet=prepStat.executeQuery();
        if (resSet.next()){
            field= new Field(id, resSet.getString("sport"), resSet.getInt("minimumPeopleRequired"), resSet.getInt("maximumPeopleRequired"), resSet.getInt("fineph"), resSet.getBoolean("availability"),resSet.getInt("sportCentre") );
        }
        resSet.close();
        prepStat.close();
        connection.close();
        return field;
    }

    @Override
    public ArrayList<Field> getAll() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        ArrayList<Field> fields = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM fields");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            fields.add(new Field(rs.getInt("ID"),rs.getString("sport"),rs.getInt("minimumPeopleRequired"),rs.getInt("maximumPeopleRequired"),rs.getInt("fineph"),rs.getBoolean("availability"),rs.getInt("sportCentre")));
        }
        rs.close();
        ps.close();
        connection.close();
        return fields;
    }


    @Override
    public void save(Field field) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO fields (ID, sport, minimumPeopleRequired,maximumPeopleRequired, fineph, availability, sportCentre) VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1,getNextId());
        ps.setString(2, field.getSport());
        ps.setInt(3, field.getMinimumPeopleRequired());
        ps.setInt(4, field.getMaximumPeopleRequired());
        ps.setInt(5, field.getFineph());
        ps.setBoolean(6, field.getAvailability());
        ps.setInt(7, field.getSportCentre().getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    @Override
    public void modify(Field field, String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("UPDATE fields SET sport = ?, minimumPeopleRequired = ?, maximumPeopleRequired = ?,fineph = ?,availability = ?   WHERE ID = ?");
        ps.setString(1, args[0]);
        ps.setInt(2, Integer.parseInt(args[1]));
        ps.setInt(3, Integer.parseInt(args[2]));
        ps.setInt(4, Integer.parseInt(args[3]));
        ps.setInt(5, Integer.parseInt(args[4]));
        ps.setInt(6, field.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM fields WHERE ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    private int getNextId() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        String query = "SELECT MAX(ID) FROM fields";
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
