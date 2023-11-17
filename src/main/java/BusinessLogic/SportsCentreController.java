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
    public SportsCentreController(SportsCentreDAO sd, FieldDAO fd, StaffDAO std) {
        this.sd = sd;
        this.fd = fd;
        this.std = std;
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

    public void createFields(int nFields, int id) throws SQLException {
        Random random=new Random();
        for (int i = 0; i < nFields; i++) {
            switch (random.nextInt(4)){
                case 0:
                    fd.save(new Field(fd.getNextId(),"tennis",2,4,35,false, sd.get(id)));
                case 1:
                    fd.save(new Field(fd.getNextId(),"beach volley",4,8,50,false, sd.get(id)));
                case 2:
                    fd.save(new Field(fd.getNextId(),"volleyball",10,12,80,false, sd.get(id)));
                case 3:
                    fd.save(new Field(fd.getNextId(),"soccer",10,22,120,false, sd.get(id)));
            }
        }
    }

    public void hireStaff(int id) throws SQLException {
        Random random=new Random();
            switch (random.nextInt(4)){
                case 0:
                    std.save(new Staff(std.getNextId(),"A","n1","s1", LocalDate.now(),"operator1",1000, sd.get(id)));
                case 1:
                    std.save(new Staff(std.getNextId(),"B","n2","s2",LocalDate.now(),"operator2",1200, sd.get(id)));
                case 2:
                    std.save(new Staff(std.getNextId(),"C","n3","s3",LocalDate.now(),"operator3",1500, sd.get(id)));
                case 3:
                    std.save(new Staff(std.getNextId(),"D","n4","s4",LocalDate.now(),"operator4",2000, sd.get(id)));
            }
    }
    private SportsCentreDAO sd;
    private FieldDAO fd;
    private StaffDAO std;
}
