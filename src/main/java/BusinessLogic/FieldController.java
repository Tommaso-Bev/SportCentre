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

    public void createField(String sport, int min, int max, int fineph, SportsCentre centre, boolean availability) throws SQLException {
        Field field = new Field(fd.getNextId(), sport, min, max, fineph, availability, centre);
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

