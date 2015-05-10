package rigAPI;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class TestRiG {
    public static void main(String[] args) throws RiGException, ParserConfigurationException, SAXException, IOException {
        RigDBAccess rig = new RigDBAccess();
        System.out.println(rig.authenticate("user1", "password1"));
        RigBand rigBand = rig.getBand();
        System.out.println(rigBand.getId());
        System.out.println(rigBand.getBewerbungsdatum());

        System.out.println(rig.getSettings());
    }
}
