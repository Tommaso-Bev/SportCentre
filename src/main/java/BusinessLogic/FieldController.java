package BusinessLogic;

import DAO.BookingDAO;
import DAO.FieldDAO;
import DomainModel.Field;
import DomainModel.SportsCentre;

import java.sql.SQLException;

public class FieldController {
    private FieldDAO fd;

    public FieldController(FieldDAO fd) {
        this.fd = fd;
    }

    public void createField(Field field) throws SQLException {
        field.setID(fd.getNextId());
        fd.save(field);
    }

    public void dismantleField(int id) throws SQLException {
        fd.remove(id);
    }

    public Field getField(int id) throws SQLException {
        return fd.get(id);
    }

    public void setFine(int id, int fine) throws SQLException {
        fd.setFine(id,fine);
    }
}

