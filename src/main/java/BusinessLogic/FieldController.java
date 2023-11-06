package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.FieldDAO;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;

import java.sql.SQLException;

public class FieldController {
    private BookingDAO bd;
    private FieldDAO fd;
    private SportsCentreController sc;

    public FieldController(BookingDAO bd, FieldDAO fd, SportsCentreController sc) {
        this.bd = bd;
        this.fd = fd;
        this.sc = sc;
    }

    public void createField(String sport, int min, int max, int fineph, SportsCentre centre, boolean availability) throws SQLException {
        Field field = new Field(fd.getNextId(), sport, min, max, fineph, availability, centre);
        fd.save(field);
    }

    public void dismantleField(int id) throws SQLException {
        fd.remove(id);
    }

    public void habilitateORdehabilitateField(int id, boolean abORdehab) throws SQLException {
        fd.habilitateField(id, abORdehab);
    }

    public Field getField(int id) throws SQLException {
        return fd.get(id);
    }

    public void setFine(int id, int fine) throws SQLException {
        fd.setFine(id,fine);
    }
}

