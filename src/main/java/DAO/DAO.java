package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<T> {
    public T get(int id) throws SQLException;

    public ArrayList<T> getAll() throws SQLException;

    public void save(T t) throws SQLException;

    public void modify(T t, String[] args) throws SQLException;

    public void remove(int id) throws SQLException;

    public int getNextId() throws SQLException ;
}
