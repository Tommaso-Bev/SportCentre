package main.java.DomainModel;

import java.time.LocalDate;

public class Staff extends Person {
    private LocalDate hiringDate;
    private String task;
    private int salary;

    public Staff(int ID, String fiscalCod, String name, String surname) {
        super(ID, fiscalCod, name, surname);
    }
}
