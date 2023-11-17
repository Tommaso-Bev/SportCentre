package main.java.DomainModel.Membership;

import java.time.LocalDate;
import java.time.Period;

public class Free implements Membership{
    private  LocalDate subscription;

    @Override
    public String getType() {
        return "Free";
    }

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Free membership, cost is zero, but you have some limitation on the time before reserve and on cancellation ";
    }

    @Override
    public int getTimeBeforeReserve() {
        return 2; //days
    }

    @Override
    public int getTimeBeforeDelete() {
        return 2; //days
    }

    @Override
    public void setSubscription() {
        subscription=LocalDate.now();
    }
    @Override
    public boolean isExpired() {
        if(LocalDate.now().isBefore(subscription.plusMonths(1))){
            return false;
        }
        return true;
    }

    @Override
    public int getDiscount() {
        return 0;
    }

}
