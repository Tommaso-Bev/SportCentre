package main.java.DomainModel;

import java.time.LocalDate;
import java.util.Date;

public class Booking {
    private int ID;
    private LocalDate date;
    private int period;

    public Booking(int ID, LocalDate date, int period){
        this.ID=ID;
        this.date=date;
        this.period=period;
    }
    public int getID(){return ID;}
    public LocalDate getDate (){return date;}
    public int getPeriod(){return period;}


}
