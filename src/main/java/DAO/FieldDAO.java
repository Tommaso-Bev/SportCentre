package main.java.DAO;
import main.java.DomainModel.Field;

import java.sql.*;
import java.util.List;

public class FieldDAO implements DAO<Field>{
    @Override
    public Field get(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.db");
        Field field=null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM fields WHERE ID= ?");
        prepStat.setInt(1, id);
        ResultSet resSet=prepStat.executeQuery();
        if (resSet.next()){
            field= new Field(resSet.getInt(id), resSet.getString("sport"), resSet.getInt("minimumPeopleRequired"), resSet.getInt("maximumPeopleRequired"), resSet.getInt("fineph"), resSet.getBoolean("availability"));
        }
        resSet.close();
        prepStat.close();
        connection.close();
        return field;
    }

    @Override
    public List<Field> getAll() {
        return null;
    }

    @Override
    public void save(Field field) {

    }

    @Override
    public void modify(Field field, Field[] args) {

    }

    @Override
    public void remove(Field field) {

    }

    @Override
    public int assignID() {
        return 0;
    }


}
