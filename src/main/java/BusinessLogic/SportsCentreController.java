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
    public SportsCentreController(SportsCentreDAO sd) {
        this.sd = sd;
    }

    public void createCentre(String name, String address, String CAP, String type) throws SQLException {
        SportsCentre s = new SportsCentre(sd.getNextId(), name, address, CAP, type);
        sd.save(s);
    }

    public void dismantleCentre(int id) throws SQLException {
        sd.remove(id);
    }

    public SportsCentre getSportCentre(int id) throws SQLException {
        return sd.get(id);
    }

    private SportsCentreDAO sd;
}
