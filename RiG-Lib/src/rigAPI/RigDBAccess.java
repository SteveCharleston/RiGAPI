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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Steven Schalhorn
 *
 */
public class RigDBAccess {

    private static String API_KEY = null;
    private static String APIURL
            = "http://bewerbung.rockimgruenen.de/api/";

    /**
     * @param args Arguments are currently unused
     */
    public static void main(String[] args) throws RiGException {
    }

    /**
     * Authenticates an user against the RiG servers. Saves the API-Key
     * internally so that other methods don't have to care about
     * authentication. Has to be called as first method after instantiating
     * this class.
     *
     * @param user          username of an RiG user
     * @param password      corresponding password of the RiG user
     * @return              returns the obtained API-Key, even though it is also
     *                      saved internally
     * @throws RiGException the errors described in the API-Documentation as
     *                      exceptions
     */
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

        API_KEY = result;

        return result;
    }

    /**
     * Retrieves band informations of a random band from the RiG server
     * @return              object with all band informations as fields
     * @throws RiGException several subclasses of RiGException
     */
    public RigBand getBand() throws RiGException {
        return getBand(null);
    }

    /**
     * Retrieves band informations from the RiG server
     * @param band          numerical id of a specific band
     * @return              object with all band informations as fields
     * @throws RiGException several subclasses of RiGException
     */
    RigBand getBand(Integer band) throws RiGException {
        String pageURL = APIURL + "read/getBand.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (band != null) {
            params.add(new BasicNameValuePair("band", band.toString()));
        }

        String result = httpPost(pageURL, params);

        if ("BAD_BAND".equals(result)) {
            throw new BadBandException();
        } else if ("GROUP_ONLY".equals(result)) {
            throw new GroupOnlyException();
        } else if ("BAND_NONEXISTANT".equals(result)) {
            throw new BandNonexistentException();
        } else if ("ROUND_COMPLETED".equals(result)) {
            throw new RoundCompletedException();
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new rigGetBandException(e);
        }

        StringBuilder xmlStringBuilder = new StringBuilder(result);
        ByteArrayInputStream input = null;
        try {
            input = new ByteArrayInputStream
                    (xmlStringBuilder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new rigGetBandException(e);
        }
        Document doc = null;
        try {
            doc = builder.parse(input);
        } catch (IOException e) {
            throw new rigGetBandException(e);
        } catch (SAXException e) {
            throw new rigGetBandException(e);
        }
        Element root = doc.getDocumentElement();

        return new RigBand(doc);
    }

    /**
     * Sends a list of parameters to a given URL and returns the response.
     * @param url                the url of the requested page
     * @param urlParameters      post parameters to be send to given url
     * @return                   Returned content from server
     * @throws httpPostException the possible IOExceptions and other exceptions,
     *                           that coule happen during communication are
     *                           wrapped
     */
    public static String httpPost(String url, List<NameValuePair> urlParameters)
            throws httpPostException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        String USER_AGENT = "Mozilla/5.0";
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

class RoundCompletedException extends RiGException {
    public RoundCompletedException(Exception e) {
        super(e);
    }

    public RoundCompletedException() {
        super();
    }
}

class BandNonexistentException extends RiGException {
    public BandNonexistentException(Exception e) {
        super(e);
    }

    public BandNonexistentException() {
        super();
    }
}

class GroupOnlyException extends RiGException {
    public GroupOnlyException(Exception e) {
        super(e);
    }

    public GroupOnlyException() {
        super();
    }
}

class BadBandException extends RiGException {
    public BadBandException(Exception e) {
        super(e);
    }

    public BadBandException() {
        super();
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

class rigGetBandException extends RiGException {
    rigGetBandException(Exception e) {
        super(e);
    }
}

