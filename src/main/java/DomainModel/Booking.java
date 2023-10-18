package main.java.DomainModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Booking {
    private int ID;
    private LocalDate date;
    private int period;
    private LocalTime time;

    public Booking(int ID, LocalDate date, int period,LocalTime t){
        this.ID=ID;
        this.date=date;
        this.period=period;
        this.time=t;
    }
    public int getID(){return ID;}
    public LocalDate getDate (){return date;}
    public int getPeriod(){return period;}
    public LocalTime getTime(){return time;}

}
