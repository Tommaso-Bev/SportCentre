package main.java.DAO;
import main.java.DomainModel.Booking;
import main.java.DomainModel.Field;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {
    public T get(int id) throws SQLException;

    public ArrayList<T> getAll() throws SQLException;

    public void save(T t) throws SQLException;

    public void modify(T t, String[] args) throws SQLException;

    void modify(Booking booking, String[] args) throws SQLException;

    public void remove(int id) throws SQLException;
    public int getNextId() throws SQLException ;
}
