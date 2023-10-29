package main.java.DomainModel.Membership;

public class Student extends DecoratorMembership {

    public Student(Membership membership) {
        super(membership);
        super.setSubscription();
    }

    @Override
    public String getType() {
        return "Student";
    }

    @Override
    public int getTimeBeforeReserve() {
        return 1000;
    }

    @Override
    public int getTimeBeforeDelete() {
        return 1000;
    }//TODO put the correct amount for all the returns

    @Override
    public float getCost() {
        return super.getCost()+1000;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Student membership, but you have less limitation on the time before reserve and on cancellation and you also get discounts when you book a DSU centre  ";
    }
}
