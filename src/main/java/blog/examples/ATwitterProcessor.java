package blog.examples;

import twitter4j.Twitter;

public abstract class ATwitterProcessor implements TwitterProcessor {

    protected final Twitter twitter;

    protected ATwitterProcessor(Twitter twitter) {
        this.twitter = twitter;
    }
}
