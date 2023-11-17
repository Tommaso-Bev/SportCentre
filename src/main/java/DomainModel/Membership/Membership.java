package main.java.DomainModel.Membership;

import java.time.LocalDate;

public interface Membership {
    String getType();

    float getCost();

    String getDescription();

    int getTimeBeforeReserve();

    int getTimeBeforeDelete();

    void setSubscription();

    boolean isExpired();
    int getDiscount();


}
