package main.java.DAO;
import main.java.DomainModel.Field;

import java.util.List;

public interface DAO<T> {
    public T get(int id);

    public List<T> getAll();

    public void save(T t);

    public void modify(T t, T[] args);

    public void remove(T t);

    public int assignID();

}
