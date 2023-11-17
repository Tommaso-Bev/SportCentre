package main.java.BusinessLogic;

import main.java.DAO.FieldDAO;
import main.java.DAO.SportsCentreDAO;
import main.java.DAO.StaffDAO;
import main.java.DomainModel.Field;
import main.java.DomainModel.SportsCentre;
import main.java.DomainModel.Staff;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class SportsCentreController {
    public SportsCentreController(SportsCentreDAO sd, FieldController fc) {
        this.sd = sd;
        this.fc=fc;
    }

    public void createCentre(SportsCentre s) throws SQLException {
        s.setID(sd.getNextId());
        sd.save(s);
    }

    public void dismantleCentre(int id) throws SQLException {
        sd.remove(id);
    }

    public SportsCentre getSportCentre(int id) throws SQLException {
        return sd.get(id);
    }

    public void createField(Field field) throws SQLException {
        fc.createField(field);
    }
    private SportsCentreDAO sd;
    private FieldController fc;
}
