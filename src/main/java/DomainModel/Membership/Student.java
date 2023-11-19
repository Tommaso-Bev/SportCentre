package DomainModel.Membership;

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
        return 5;
    }

    @Override
    public int getTimeBeforeDelete() {
        return 2;
    }

    @Override
    public float getCost() {
        return super.getCost()+5;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Student membership, but you have less limitation on the time before reserve and on cancellation and you also get discounts when you book from DSU centre  ";
    }

    @Override
    public int getDiscount(){
        return 25;
    }
}
