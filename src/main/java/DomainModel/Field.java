package main.java.DomainModel;

public class Field {
    public Field(int ID, String sport, int minimumPeopleRequired, int maximumPeopleRequired, int fineph, boolean availability) {
        this.ID = ID;
        this.sport = sport;
        this.minimumPeopleRequired = minimumPeopleRequired;
        this.maximumPeopleRequired = maximumPeopleRequired;
        this.fineph = fineph;
        this.availability = availability;
    }

    public Field (int ID){
        this.ID=ID;
    }

    private int ID;
    private String sport;
    private int minimumPeopleRequired;
    private int maximumPeopleRequired;
    private int fineph;



    private boolean availability;
}
