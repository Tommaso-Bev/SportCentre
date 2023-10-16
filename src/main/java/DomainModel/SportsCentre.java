package main.java.DomainModel;

public class SportsCentre {
    private int ID;
    private String name;
    private String address;
    private String CAP;
    private String type;

    public SportsCentre(int ID, String name, String address, String CAP, String type) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.CAP = CAP;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCAP() {
        return CAP;
    }

    public String getAddress() {
        return address;
    }
}
