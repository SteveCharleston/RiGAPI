/**
 *
 */
package rigAPI;


import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.classpath.icedtea.pulseaudio.PulseAudioMixerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import build.tools.javazic.Main;

/**
 * @author steven
 *
 */
public class RigDBAccess {

    private String API_KEY;
    private static String USER_AGENT = "Mozilla/5.0";
    private static String APIURL
            = "http://bewerbung.rockimgruenen.de/api/";
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, RiGException {
    }

    public String authenticate(String user, String password) throws
            RiGException, IOException {
        String pageURL = APIURL + "authenticate.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("password", password));

        String result = httpPost(pageURL, params);

        if ("NO_USER".equals(result)) {
            throw new NoUserException();
        } else if ("NO_PASSWORD".equals(result)) {
            throw new NoPasswordException();
        } else if ("BAD_AUTHENTICATION".equals(result)) {
            throw new BadAuthenticationException();
        } else if ("BROKEN_APIKEY".equals(result)) {
            throw new BrokenAPIKeyException();
        }

        this.API_KEY = result;

        return result;
    }

    public void getBand(List<NameValuePair> params) {
        String pageURL = APIURL + "getBand.php";

        for (NameValuePair param : params) {

        }
    }

    public static String httpPost(String url, List<NameValuePair> urlParameters)
            throws RiGException, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader
                (response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
class RiGException extends Exception { }

class NoUserException extends RiGException { }

class NoPasswordException extends RiGException { }

class BadAuthenticationException extends RiGException { }

class BrokenAPIKeyException extends RiGException { }

