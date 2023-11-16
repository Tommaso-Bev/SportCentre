package main.java.BusinessLogic;

import java.sql.SQLException;

public interface Subject {

    public void  notifyobservers(int ID) throws SQLException;
}
