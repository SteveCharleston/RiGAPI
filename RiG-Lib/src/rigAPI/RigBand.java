package rigAPI;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by steven on 05.05.15.
 */
public class RigBand {
    private int id;
    private int bewerbungsdatum;
    private int uid;
    private String status;
    private int runde;
    private int result;
    private String day;
    private String name;
    private String proberaum;
    private List<String> beschreibung;
    private List<String> besetzung;
    private String musikstil;
    private String voice;
    private String gruendung;
    private String covermusik;
    private int titel;
    private boolean gema;
    private String kontakt_nachname;
    private String kontakt_vorname;
    private String kontakt_adresse;
    private String kontakt_plz;
    private String kontakt_ort;
    private String kontakt_telefon;
    private String kontakt_email;
    private String homepage;
    private String facebook;
    private String soundcloud;
    private String youtube;
    private String backstage;
    private List<String> kommentar;
    private String woher;
    private List<Picture> pictures;
    private List<Song> songs;
    private List<String> voters;
    private List<String> tags;

    public RigBand(Document doc) {
    }
}
