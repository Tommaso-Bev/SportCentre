package DomainModel;

import DomainModel.Membership.Membership;
import BusinessLogic.Observer;
import DomainModel.Membership.Membership;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class User extends Person implements Observer {
    private LocalDate inscriptionDate;
    private Membership membership;
    public User(int ID, String fiscalCod, String name, String surname, LocalDate inscriptionDate, Membership membership) {
        super(ID, fiscalCod, name, surname);
        this.inscriptionDate = inscriptionDate;
        this.membership = membership;
    }

    @Override
    public void update(String message) {
        System.out.println("User " + this.getName() + " " + this.getSurname() + message);
    }

    public String getInscriptionDate() {
        return inscriptionDate.toString();
    }

    public String getMembershipName() {
        return membership.getType();
    }

    public boolean canBook(LocalDate date){
        LocalDate now=LocalDate.now();
        long dayBetween= ChronoUnit.DAYS.between(now,date);
        if(dayBetween<= membership.getTimeBeforeReserve()) return true;
        else return false;
    }
    public boolean canDeleteModifyBook(LocalDate date){
        LocalDate now=LocalDate.now();
        long dayBetween= ChronoUnit.DAYS.between(now,date);
        if(dayBetween>= membership.getTimeBeforeDelete()) return true;
        else return false;
    }
}
