package DomainModel.Membership;

public class Basic extends DecoratorMembership {

    public Basic(Membership membership) {
        super(membership);
        super.setSubscription();
    }

    @Override
    public String getType() {
        return "Basic";
    }

    @Override
    public int getTimeBeforeReserve() {
        return 3;
    }

    @Override
    public int getTimeBeforeDelete() {
        return 2;
    }

    @Override
    public float getCost() {
        return super.getCost()+6;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Basic membership, but you have less limitation on the time before reserve and on cancellation  ";
    }
    @Override
    public int getDiscount(){
        return 0;
    }
}
