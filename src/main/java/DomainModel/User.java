package main.java.DomainModel;

import main.java.BusinessLogic.Observer;
import main.java.DomainModel.Membership.Membership;

import java.time.LocalDate;

public class User extends Person implements Observer {
    private LocalDate inscriptionDate;
    private Membership membership;

    public User(int ID, String fiscalCod, String name, String surname){
        super(ID,fiscalCod,name,surname);
    }

    @Override
    public void update() {
        System.out.println("User " + this.getName() + " " + this.getSurname() + " has been notified of the lesson cancellation");
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }


}
