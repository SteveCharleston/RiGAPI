package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Contains all fields returned by getSettings.php as fields
 */
public class RigSettings extends  ClassFromXML {
    private String status;
    private Integer year;
    private int neccessary_votes;
    private List<Integer> neccessary_votes_special;
    private List<String> tags_voice;
    private List<String> tags_day;
    private List<String> tags_music;
    private int limit_vote_min;
    private int limit_vote_max;
    private int limit_tag_min;
    private int limit_tag_max;
    private int limit_day_min;
    private int limit_day_max;
    private int limit_bands;

    /**
     * Sets up the object according to the fields in the retrieved xml
     * document after calling getSettings.php
     * @param doc the document returned from the RiG server
     */
    public RigSettings(Document doc) {
        super(doc);

        status = getContent("status");
        year = Integer.parseInt(getContent("year"));
        neccessary_votes = Integer.parseInt(getContent("necessary_votes"));

        neccessary_votes_special = new ArrayList<Integer>();
        NodeList specialVotes = getChildEntities("necessary_votes_special");
        for (int i = 0; i < specialVotes.getLength(); i++) {
            neccessary_votes_special.add(
                    Integer.parseInt(specialVotes.item(i).getTextContent()));
        }

        tags_voice = new ArrayList<String>();
        NodeList voiceEntities = getChildEntities("voice");
        for (int i = 0; i < voiceEntities.getLength(); i++) {
            tags_voice.add(voiceEntities.item(i).getTextContent());
        }

        tags_day = new ArrayList<String>();
        NodeList dayEntities = getChildEntities("day");
        for (int i = 0; i < dayEntities.getLength(); i++) {
            tags_day.add(dayEntities.item(i).getTextContent());
        }

        tags_music = new ArrayList<String>();
        NodeList musicEntities = getChildEntities("music");
        for (int i = 0; i < musicEntities.getLength(); i++) {
            tags_music.add(musicEntities.item(i).getTextContent());
        }

        String nodename;
        String localname;
        NodeList limitsNodes = getChildEntities("limits");
        for (int i = 0; i < limitsNodes.getLength(); i++) {
            nodename = limitsNodes.item(i).getNodeName();
            localname = limitsNodes.item(i).getLocalName();

            System.out.println(nodename);
            System.out.println(localname);
        }
    }
}
