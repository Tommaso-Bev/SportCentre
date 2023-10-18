package main.java.DomainModel;

import java.time.LocalDate;

public class Staff extends Person {
    private LocalDate hiringDate;
    private String task;
    private int salary;

    private SportsCentre sportsCentre;

    public Staff(int ID, String fiscalCod, String name, String surname, LocalDate hiringDate, String task, int salary, SportsCentre sportsCentre) {
        super(ID, fiscalCod, name, surname);
        this.hiringDate = hiringDate;
        this.task = task;
        this.salary = salary;
        this.sportsCentre=sportsCentre;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public String getTask() {
        return task;
    }

    public int getSalary() {
        return salary;
    }

    public SportsCentre getSportsCentre() {
        return sportsCentre;
    }

}
