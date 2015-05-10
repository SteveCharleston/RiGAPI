package rigAPI;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Contains all fields returned by getSettings.php as fields
 */
public class RigSettings extends  ClassFromXML {
    private String status;
    private int year;
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
        neccessary_votes = Integer.parseInt(getContent("neccessary_votes"));
    }
}
