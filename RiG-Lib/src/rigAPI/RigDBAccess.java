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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public static void main(String[] args) {

        List<NameValuePair> loginParams = new ArrayList<NameValuePair>(2);
        loginParams.add(new BasicNameValuePair("user", "user2"));
        //loginParams.add(new BasicNameValuePair("password",
                //"7c6a180b36896a0a8c02787eeafb0e4c"));
        loginParams.add(new BasicNameValuePair("password",
                "6cb75f652a9b52798eb6cf2201057c73"));

        try {
            String loginRet = httpPost(url, loginParams);
            System.out.println(loginRet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void authenticate(List<NameValuePair> params) throws
            Exception, NoUserException {
        String pageURL = APIURL + "authenticate.php";

        String result = httpPost(pageURL, params);

        if ("NO_USER".equals(result)) {
            throw new NoUserException();
        } else if ("NO_PASSWORD".equals(result)) {
            throw new NoPasswordException();
        } else if ("BAD_AUTHENTICATON".equals(result)) {
            throw new BadAuthenticationException();
        } else if ("BROKEN_APIKEY".equals(result)) {
            throw new BrokenAPIKeyException();
        }

        this.API_KEY = result;
    }

    public static String httpPost(String url, List<NameValuePair> urlParameters)
            throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader
                (response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    private static class NoUserException extends Exception { }

    private static class NoPasswordException extends Exception { }

    private static class BadAuthenticationException extends Exception { }

    private static class BrokenAPIKeyException extends Exception { }
}


