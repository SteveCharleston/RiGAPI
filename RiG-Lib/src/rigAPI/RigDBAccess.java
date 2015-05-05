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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author steven
 *
 */
public class RigDBAccess {

    private static String API_KEY = null;
    private static String USER_AGENT = "Mozilla/5.0";
    private static String APIURL
            = "http://bewerbung.rockimgruenen.de/api/";

    /**
     * @param args
     */
    public static void main(String[] args) throws RiGException {
    }

    public String authenticate(String user, String password) throws
            RiGException {
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

    public String getBand() throws RiGException, IOException, SAXException,
            ParserConfigurationException {
        return getBand(null);
    }

    public String getBand(Integer band) throws RiGException, ParserConfigurationException, IOException, SAXException {
        String pageURL = APIURL + "read/getBand.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (band != null) {
            params.add(new BasicNameValuePair("band", band.toString()));
        }

        String result = httpPost(pageURL, params);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder(result);
        ByteArrayInputStream input = new ByteArrayInputStream
                (xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        Element root = doc.getDocumentElement();

        return result;
    }

    public static String httpPost(String url, List<NameValuePair> urlParameters)
            throws RiGException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);

        urlParameters.add(new BasicNameValuePair("apikey", API_KEY));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader
                    (response.getEntity().getContent()));
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        StringBuffer result = new StringBuffer();
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        return result.toString();
    }

}
class RiGException extends Exception {
    public RiGException(Exception e) {
        super(e);
    }

    public RiGException() {
    }
}

class NoUserException extends RiGException {
    public NoUserException(Exception e) {
        super(e);
    }

    public NoUserException() {
        super();
    }
}

class NoPasswordException extends RiGException {
    public NoPasswordException(Exception e) {
        super(e);
    }

    public NoPasswordException() {
    }
}

class BadAuthenticationException extends RiGException {
    public BadAuthenticationException(Exception e) {
        super(e);
    }

    public BadAuthenticationException() {
    }
}

class BrokenAPIKeyException extends RiGException {
    public BrokenAPIKeyException(Exception e) {
        super(e);
    }

    public BrokenAPIKeyException() {
    }
}

class httpPostException extends RiGException {
    httpPostException(Exception e) {
        super(e);
    }
}

