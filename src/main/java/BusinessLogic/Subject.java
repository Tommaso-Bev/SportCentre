package BusinessLogic;

import java.sql.SQLException;

public interface Subject {

    public void  notifyobservers(int ID, String message) throws SQLException;
}
