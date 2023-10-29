package main.java.DomainModel;

import main.java.BusinessLogic.Observer;
import main.java.DomainModel.Membership.Membership;

import java.time.LocalDate;

public class User extends Person implements Observer {
    private LocalDate inscriptionDate;
    private Membership membership;
    public User(int ID, String fiscalCod, String name, String surname, LocalDate inscriptionDate, Membership membership) {
        super(ID, fiscalCod, name, surname);
        this.inscriptionDate = inscriptionDate;
        this.membership = membership;
    }

    @Override
    public void update() {
        System.out.println("User " + this.getName() + " " + this.getSurname() + " has been notified of the lesson cancellation");
    }

    public String getInscriptionDate() {
        return inscriptionDate.toString();
    }

    public String getMembershipName() {
        return membership.getType();
    }
}
