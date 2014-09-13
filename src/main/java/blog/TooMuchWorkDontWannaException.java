package blog;

import twitter4j.TwitterException;

public class TooMuchWorkDontWannaException extends TwitterException {

    public TooMuchWorkDontWannaException(String message) {
        super(message);
    }

}
