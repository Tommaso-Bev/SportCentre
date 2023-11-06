package main.java.DomainModel;

public class Field {
    public Field(int ID, String sport, int minimumPeopleRequired, int maximumPeopleRequired, int fineph, boolean availability, SportsCentre centre) {
        this.ID = ID;
        this.sport = sport;
        this.minimumPeopleRequired = minimumPeopleRequired;
        this.maximumPeopleRequired = maximumPeopleRequired;
        this.fineph = fineph;
        this.availability = availability;
        this.centre = centre;
    }

    public String getSport() {
        return sport;
    }

    public int getMinimumPeopleRequired() {
        return minimumPeopleRequired;
    }

    public int getMaximumPeopleRequired() {
        return maximumPeopleRequired;
    }


    public int getFineph() {
        return fineph;
    }

    public boolean getAvailability() {
        return availability;
    }

    public SportsCentre getSportCentre() {
        return centre;
    }

    public int getId() {
        return ID;
    }


    private int ID;
    private String sport;
    private int minimumPeopleRequired;
    private int maximumPeopleRequired;
    private int fineph;

    private SportsCentre centre;
    private boolean availability;
}
