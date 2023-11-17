package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.FieldDAO;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;

import java.sql.SQLException;

public class FieldController {
    private FieldDAO fd;

    public FieldController(BookingDAO bd, FieldDAO fd, SportsCentreController sc) {
        this.fd = fd;
    }

    public void createField(Field field) throws SQLException {
        field.setID(fd.getNextId());
        fd.save(field);
    }

    public void dismantleField(int id) throws SQLException {
        fd.remove(id);
    }

    public void habilitateOrRehabilitateField(int id, boolean abOrRehab) throws SQLException {
        fd.habilitateField(id, abOrRehab);
    }

    public Field getField(int id) throws SQLException {
        return fd.get(id);
    }

    public void setFine(int id, int fine) throws SQLException {
        fd.setFine(id,fine);
    }
}

