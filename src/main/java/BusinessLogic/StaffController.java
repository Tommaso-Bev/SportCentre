package main.java.BusinessLogic;

import main.java.DAO.BookingDAO;
import main.java.DAO.StaffDAO;

public class StaffController {
    private SportsCentreController sc;
    private BookingController bc;
    private StaffDAO sd;

    public StaffController(SportsCentreController sc, BookingController bc, StaffDAO sd) {
        this.sc = sc;
        this.bc = bc;
        this.sd = sd;
    }
}
