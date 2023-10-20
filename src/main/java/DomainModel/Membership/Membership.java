package main.java.DomainModel.Membership;

import java.time.LocalDate;

public interface Membership {
    public String getType();
    public float getCost();
    public String getDescription();
    public int getTimeBeforeReserve();
    public int getTimeBeforeDelete();
    public void setSubscription();
    public boolean isExpired();


}
