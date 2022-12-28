
import observer.ConcreteMember;
import observer.GroupAdmin;
import org.junit.jupiter.api.Test;
import observer.UndoableStringBuilder;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);


    /**
     * This test made for checking the register method in the GroupAdmin class.
     * we are also using JvmUtilities to get information about the storage and the data processes that been made.
     * when we create the admin and the members we use objectFootprint to see the refernces that object made.
     *
     * in the end of the test we are making comparison between the sizes of the two members we created,
     * using the objectTotalSize and assertEquals.
     *
     * we also use the jvmInfo to get the amount of storage that allocated for our program
     */
    @Test
    void registerTest() {
        logger.info(() -> JvmUtilities.jvmInfo());
        ConcreteMember member1 = new ConcreteMember();
        ConcreteMember member2 = new ConcreteMember();
        GroupAdmin admin = new GroupAdmin();
        logger.info(() -> JvmUtilities.objectFootprint(admin));
        logger.info(() -> JvmUtilities.objectFootprint(member1));
        admin.register(member1);
        admin.register(member2);
        logger.info(() -> JvmUtilities.objectFootprint(member2));
        admin.register(member2);
        logger.info(() -> JvmUtilities.objectFootprint(member2));
        admin.members.contains(member1);
        admin.members.contains(member2);
        logger.info(() -> ("size of coc2:")+JvmUtilities.objectTotalSize(member2));
        logger.info(() -> ("size of coc1:")+JvmUtilities.objectTotalSize(member1));
        assertEquals(JvmUtilities.objectTotalSize(member1),JvmUtilities.objectTotalSize(member2));
    }

    /**
     * this test made for the unregister method, we want to find out that the user is actually removed form the group admin list.
     * furthermore we added the objectTotalSize method to get assumptions on the changes that occured in the heap via the action.
     *
     */
    @Test
    void unregisterTest() {
        logger.info(() -> JvmUtilities.jvmInfo());
        ConcreteMember member1 = new ConcreteMember();
        GroupAdmin admin = new GroupAdmin();
        admin.register(member1);
        logger.info(() -> JvmUtilities.objectTotalSize(admin.members));
        admin.members.contains(member1);
        admin.members.remove(member1);
        logger.info(() ->JvmUtilities.objectTotalSize(admin.members));
        assertEquals(admin.members.contains(member1), false);
    }

    /**
     *this test made to check to notifyAll method,which is the main method in the observer design pattren.
     * we want to assure that all the members are got the same pointer of the last update that been made by the admin.
     */
    @Test
    void allBeenUpdated() {
        logger.info(() -> JvmUtilities.jvmInfo());
        GroupAdmin admin = new GroupAdmin();
        admin.append("Noam");
        ConcreteMember member1 = new ConcreteMember();
        admin.register(member1);
        admin.append("sss");
        logger.info(() -> JvmUtilities.objectTotalSize(member1));
        admin.undo();
        assertEquals(admin.toString(), "Noam");
        assertEquals(member1.toString(), "Noam");

    }

    /**
     *this test made to assure that the new subscriber is not aware of the updates that made preior to he's register.
     */
    @Test
    void newSubscriber() {
        logger.info(() -> JvmUtilities.jvmInfo());
        GroupAdmin admin = new GroupAdmin();
        admin.append("Noam");
        ConcreteMember member1 = new ConcreteMember();
        admin.register(member1);
        assertEquals(member1.usb, null);
    }


    /**
     * in this test making a check on the ConcreteMember class.
     *in the first part we check if the subscriber get the update that been in the admin usb before he registerd.
     *in the second part we want to see the diffrenced in the data before and after the admin append a String.
     *in the third part we unregoster and register the member and we want to know that in the gap of time
     * he didnt notified about the updates/
     */
    @Test
    public void TestConcreteMember() {
        ConcreteMember mem1 = new ConcreteMember();
        logger.info(() -> JvmUtilities.objectTotalSize(mem1));
        GroupAdmin mem = new GroupAdmin();
        //Test if mem1 update to the first String "wel"
        mem.append("wel");
        mem.register(mem1);
        mem.append("check");
        mem.undo();
        assertEquals("wel",mem1.getUsb().toString());
        //Test if mem1 update to the new String "welcome"
        mem.append("come");
        assertEquals("welcome", mem1.getUsb().toString());

        // checking with logger if the size of the object is changing
        logger.info(() -> JvmUtilities.objectTotalSize(mem1));
        mem.append(" to our channel");
        logger.info(() -> JvmUtilities.objectTotalSize(mem1));

        // Testing the usb after unregistering from the GroupAdmin
        mem.unregister(mem1);
        UndoableStringBuilder usb2 = new UndoableStringBuilder();
        usb2.append("nn");
        mem1.update(usb2);//this action supposed to do nothing
        //checking if the update make any unuseful change or didn't make change as we want
        assertEquals("nn", mem1.getUsb().toString());
        //check if the size changing after unregister
        logger.info(() -> JvmUtilities.objectTotalSize(mem1));
    }
    }