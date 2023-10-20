package main.java.DomainModel.Membership;

import java.time.LocalDate;

public abstract class DecoratorMembership implements Membership {
    protected Membership membership;
    public DecoratorMembership(Membership membership){
        this.membership=membership;
    }

    @Override
    public void setSubscription() {
    }

    @Override
    public String getType() {
        return membership.getType();
    }

    @Override
    public int getTimeBeforeReserve() {
        return membership.getTimeBeforeReserve();
    }

    @Override
    public int getTimeBeforeDelete() {
        return membership.getTimeBeforeDelete();
    }

    @Override
    public float getCost() {
        return membership.getCost();
    }

    @Override
    public String getDescription() {
        return membership.getDescription();
    }

    @Override
    public boolean isExpired() {
        return membership.isExpired();
    }
}
