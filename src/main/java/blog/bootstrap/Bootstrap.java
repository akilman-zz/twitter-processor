package blog.bootstrap;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.*;

public class Bootstrap {

    public static final String ACCESS_TOKEN = "access-token";

    public static Twitter getTwitterInstance() throws Exception {

        AccessToken accessToken = loadAccessToken();
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthAccessToken(accessToken);

        return twitter;
    }

    public static void main(String[] args) throws Exception {
        fetchAndStoreAccessToken();
    }

    public static void fetchAndStoreAccessToken() throws Exception {

        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        //persist to the accessToken for future reference.
        storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
    }

    public static void storeAccessToken(long useId, AccessToken accessToken) throws IOException {

        FileOutputStream fout = new FileOutputStream(ACCESS_TOKEN);
        ObjectOutputStream oos = new ObjectOutputStream(fout);

        oos.writeObject(accessToken);
    }

    public static AccessToken loadAccessToken() throws IOException, ClassNotFoundException {

        File accessToken = new File(ACCESS_TOKEN);
        if (!accessToken.exists()) {
            return null;
        }

        FileInputStream streamIn = new FileInputStream(ACCESS_TOKEN);
        ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
        AccessToken result = (AccessToken) objectinputstream.readObject();
        return result;
    }


}
