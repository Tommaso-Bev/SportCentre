package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.StaffDAO;
import main.java.DomainModel.SportsCentre;
import main.java.DomainModel.Staff;

import java.sql.SQLException;
import java.time.LocalDate;

public class StaffController {
    public StaffController(SportsCentreController sc, BookingController bc, StaffDAO sd) {
        this.sc = sc;
        this.bc = bc;
        this.sd = sd;
    }

    public void hireStaff(String codFisc, String firstName, String surname, LocalDate hiringDate, String task, int salary, SportsCentre sportsCentre) throws SQLException {
        Staff staff = new Staff(sd.getNextId(), codFisc, firstName, surname, hiringDate, task, salary, sportsCentre);
        sd.save(staff);
    }

    public void fireStaff(int id) throws SQLException {
        sd.remove(id);
    }

    public Staff getStaff(int id) throws SQLException {
        return sd.get(id);
    }

    public String removeBooking(int id){

    }
    private SportsCentreController sc;
    private BookingController bc;
    private StaffDAO sd;

}
