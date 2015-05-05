package rigAPI;

/**
 * Created by steven on 06.05.15.
 */
public class Picture {
    private int id;
    private String local;
    private String url;

    public Picture(int id, String local, String url) {
        this.id = id;
        this.local = local;
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
