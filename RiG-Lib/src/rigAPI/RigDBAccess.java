/**
 * 
 */
package rigAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author steven
 *
 */
public class RigDBAccess {

    private final String USER_AGENT = "Mozilla/5.0";
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public static void httpPost(String url, List<NameValuePair> urlParameters)
        throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
    }

}
