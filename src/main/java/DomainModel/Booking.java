package main.java.DomainModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Booking {
    private int ID;
    private LocalDate date;
    private float period; //duration of the booking
    private LocalTime time;
    private User user;
    private Field field;

    public Booking(int ID, LocalDate date, float period,LocalTime time, User user,Field field){
        this.ID=ID;
        this.date=date;
        this.period=period;
        this.time=time;
        this.user=user;
        this.field=field;
    }
    public int getID(){return ID;}
    public LocalDate getDate (){return date;}
    public float getPeriod(){return period;}
    public LocalTime getTime(){return time;}
    public User getUser() {
        return user;
    }
    public Field getField() {
        return field;
    }
}
