package main.java.DAO;
import main.java.DomainModel.Field;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FieldDAO implements DAO<Field>{
    @Override
    public Field get(int id) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:"+"sportCentre.db");
        Field field=null;
        PreparedStatement prepStat=connection.prepareStatement("SELECT * FROM fields WHERE id= ?");
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
