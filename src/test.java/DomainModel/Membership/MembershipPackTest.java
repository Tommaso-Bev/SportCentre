package DomainModel.Membership;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MembershipPackTest {

    @Test
    public void TestSetSubscription() {
        Membership g = new Free();
        Membership m = new Basic(g);
        Membership l = new Student(m);
        g.setSubscription();
        l.setSubscription();
        Assertions.assertFalse(g.isExpired());
        Assertions.assertFalse(l.isExpired());
    }

    @Test
    public void TestMembershipFree() {
        Membership f = new Free();
        Assertions.assertEquals("Free",f.getType());
        Assertions.assertEquals(2,f.getTimeBeforeReserve());
        Assertions.assertEquals(2,f.getTimeBeforeDelete());
        Assertions.assertEquals(0,f.getCost());
        Assertions.assertEquals(0,f.getDiscount());
    }
    @Test
    public void TestMembershipBasic() {
        Membership f = new Free();
        Membership b = new Basic(f);
        Assertions.assertEquals("Basic",b.getType());
        Assertions.assertEquals(3,b.getTimeBeforeReserve());
        Assertions.assertEquals(2,b.getTimeBeforeDelete());
        Assertions.assertEquals(6,b.getCost());
        Assertions.assertEquals(0,b.getDiscount());
    }
    @Test
    public void TestMembershipStudent() {
        Membership f = new Free();
        Membership b = new Basic(f);
        Membership s = new Student(b);
        Assertions.assertEquals("Student",s.getType());
        Assertions.assertEquals(5,s.getTimeBeforeReserve());
        Assertions.assertEquals(5,s.getTimeBeforeDelete());
        Assertions.assertEquals(11,s.getCost());
        Assertions.assertEquals(25,s.getDiscount());
    }
    @Test
    public void TestMembershipPremium() {
        Membership f = new Free();
        Membership b = new Basic(f);
        Membership p = new Premium(b);
        Assertions.assertEquals("Premium",p.getType());
        Assertions.assertEquals(10,p.getTimeBeforeReserve());
        Assertions.assertEquals(10,p.getTimeBeforeDelete());
        Assertions.assertEquals(16,p.getCost());
        Assertions.assertEquals(20,p.getDiscount());
    }


}
