package main.java.BusinessLogic;

import main.java.DAO.FieldDAO;
import main.java.DAO.SportsCentreDAO;
import main.java.DAO.StaffDAO;
import main.java.DomainModel.SportsCentre;

import java.sql.SQLException;

public class SportsCentreController {
    public SportsCentreController(SportsCentreDAO sd, FieldDAO fd, StaffDAO std) {
        this.sd = sd;
        this.fd = fd;
        this.std = std;
    }

    public void createCentre(int id, String name, String address, String CAP, String type) throws SQLException {
        SportsCentre s = new SportsCentre(id, name, address, CAP, type);
        sd.save(s);
    }

    public void dismantleCentre(int id) throws SQLException {
        sd.remove(id);
    }

    public SportsCentre getSportCentre(int id) throws SQLException {
        return sd.get(id);
    }

    private SportsCentreDAO sd;
    private FieldDAO fd;
    private StaffDAO std;
}
