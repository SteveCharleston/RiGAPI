package rigAPI;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class TestRiG {
    public static void main(String[] args) throws RiGException, ParserConfigurationException, SAXException, IOException {
        RigDBAccess rig = new RigDBAccess();
        System.out.println(rig.authenticate("user1", "password1"));
        System.out.println(rig.getBand());
        System.out.println(rig.getSettings());
        System.out.println(rig.getStatistic());
    }
}
