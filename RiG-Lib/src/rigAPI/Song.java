package rigAPI;

/**
 * Created by steven on 06.05.15.
 */
public class Song {
    private Integer id;
    private String local;
    private String url;

    public Song() {
        this.id = null;
        this.local = null;
        this.url = null;
    }
    public Song(int id, String local, String url) {
        this.id = id;
        this.local = local;
        this.url = url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocal(String local) {

        this.local = local;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getLocal() {

        return local;
    }

    public String getUrl() {

        return url;
    }
}
