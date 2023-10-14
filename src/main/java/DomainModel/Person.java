package main.java.DomainModel;

public abstract class Person {
    private int ID;
    private String codFisc;
    private String name;
    private String surname;

    public Person(int ID, String fiscalCod, String name, String surname) {
        this.ID=ID;
        this.codFisc=fiscalCod;
        this.name=name;
        this.surname=surname;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    protected String getName() {
        return name;
    }

    protected String getSurname() {
        return surname;
    }
}
