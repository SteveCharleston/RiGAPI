package rigAPI;

import java.io.IOException;

public class TestRiG {
    public static void main(String[] args) throws RiGException {
        RigDBAccess rig = new RigDBAccess();
        System.out.println(rig.authenticate("user1", "password1"));
        System.out.println(rig.getBand());
    }
}
