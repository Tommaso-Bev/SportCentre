package main.java.DomainModel.Membership;

public class Basic extends DecoratorMembership {

    public Basic(Membership membership) {
        super(membership);
    }

    @Override
    public String getType() {
        return "Basic";
    }

    @Override
    public int getTimeBeforeReserve() {
        return 1000;
    }

    @Override
    public int getTimeBeforeDelete() {
        return 1000;
    }

    @Override
    public float getCost() {
        return super.getCost()+1000;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Basic membership, but you have less limitation on the time before reserve and on cancellation  ";
    }
}
