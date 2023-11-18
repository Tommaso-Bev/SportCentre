package DomainModel.Membership;

public class Premium extends DecoratorMembership {

    public Premium(Membership membership) {
        super(membership);
        super.setSubscription();
    }

    @Override
    public String getType() {
        return "Premium";
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
        return super.getDescription()+" Premium membership, but you have less limitation on the time before reserve and on cancellation with also get discounts  ";
    }

    @Override
    public int getDiscount(){
        return 20;
    }
}