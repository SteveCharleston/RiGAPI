package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 05.05.15.
 */
public class RigBand {
    private Integer id;
    private Integer bewerbungsdatum;
    private String uid;
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
    private Document doc;

    /**
     * Sets up the object according to the fields in the retrieved xml
     * document one gets when requesting a Band and its infos.
     * @param doc the document returned from the RiG server
     */
    public RigBand(Document doc) {
        this.doc = doc;
        this.xml = doc.getTextContent();

        Element e = (Element) doc
                .getElementsByTagName("rig_band")
                .item(0);
        id = Integer.parseInt(e.getAttribute("id"));

        bewerbungsdatum =  Integer.parseInt(getContent("bewerbungsdatum"));
        uid = getContent("uid");
        status = getContent("status");
        runde = Integer.parseInt(getContent("runde"));
        result = Integer.parseInt(getContent("result"));
        day = getContent("day");
        name = getContent("name");
        proberaum = getContent("proberaum");

        beschreibung = new ArrayList<String>();
        NodeList beschreibungParas = getChildEntities("beschreibung");
        for (int i = 0; i < beschreibungParas.getLength(); i++) {
            beschreibung.add(beschreibungParas.item(i).getTextContent());
        }

        besetzung = new ArrayList<String>();
        NodeList bandMitglieder = getChildEntities("besetzung");
        for (int i = 0; i < bandMitglieder.getLength(); i++) {
            besetzung.add(bandMitglieder.item(i).getTextContent());
        }

        musikstil = getContent("musikstil");
        voice = getContent("voice");
        gruendung = getContent("gruendung");
        covermusik = getContent("covermusik");
        titel = Integer.parseInt(getContent("titel"));

        gema = getContent("gema").equals("ja") ? true : false;

        kontakt_nachname = getContent("nachname");
        kontakt_vorname = getContent("vorname");
        kontakt_adresse = getContent("adresse");
        kontakt_plz = getContent("plz");
        kontakt_ort = getContent("ort");
        kontakt_telefon = getContent("telefon");
        kontakt_email = getContent("email");

        homepage = getContent("homepage");
        facebook = getContent("facebook");
        soundcloud = getContent("soundcloud");
        youtube = getContent("youtube");
        backstage = getContent("backstage");

        kommentar = new ArrayList<String>();
        NodeList kommentarParas = getChildEntities("kommentar");
        for (int i = 0; i < kommentarParas.getLength(); i++) {
            kommentar.add(kommentarParas.item(i).getTextContent());
        }

        pictures = new ArrayList<Picture>();
        NodeList pictureEntities = getChildEntities("pictures");
        for (int i = 0; i < pictureEntities.getLength(); i++) {
            pictures.add(instPicture(pictureEntities.item(i)));
        }

        songs = new ArrayList<Song>();
        NodeList songEntities = getChildEntities("pictures");
        for (int i = 0; i < songEntities.getLength(); i++) {
            songs.add(instSong(songEntities.item(i)));
        }

        voters = new ArrayList<String>();
        NodeList voterEntities = getChildEntities("voters");
        for (int i = 0; i < voterEntities.getLength(); i++) {
            voters.add(voterEntities.item(i).getTextContent());
        }

        tags = new ArrayList<String>();
        NodeList tagEntities = getChildEntities("tags");
        for (int i = 0; i < tagEntities.getLength(); i++) {
            tags.add(tagEntities.item(i).getTextContent());
        }
    }

    private Picture instPicture(Node item) {
        Element e = (Element) item;
        Picture pic = new Picture();

        pic.setId(Integer.parseInt(e.getAttribute("id")));
        pic.setLocal(e.getAttribute("local"));
        pic.setUrl(e.getTextContent());

        return pic;
    }

    private Song instSong(Node item) {
        Element e = (Element) item;
        Song song = new Song();

        song.setId(Integer.parseInt(e.getAttribute("id")));
        song.setLocal(e.getAttribute("local"));
        song.setUrl(e.getTextContent());

        return song;
    }

    /**
     * Retrieves all childnodes of a given tagname
     * @param tagname the name of the tag whose child elements should be
     *                retrieved
     * @return        childelements of the given tag
     */
    private NodeList getChildEntities(String tagname) {
        return doc
                .getElementsByTagName(tagname)
                .item(0)
                .getChildNodes();
    }

    /**
     * Retrieves the Text content of the first element in the given document
     * with the given tagname
     * @param tagname   tagname whose inner text we want to get
     * @return          the inner text of the given tagname
     */
    private String getContent(String tagname) {
        return doc.getElementsByTagName(tagname).item(0).getTextContent();
    }
}
