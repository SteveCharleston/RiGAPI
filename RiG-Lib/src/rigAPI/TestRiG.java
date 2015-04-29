package rigAPI;

import java.io.IOException;

public class TestRiG {
    public static void main(String[] args) throws IOException, RiGException {
        RigDBAccess rig = new RigDBAccess();
        System.out.println(rig.authenticate("user2", "password1"));
    }
}
