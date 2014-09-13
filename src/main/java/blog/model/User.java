package blog.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final List<Tweet> tweets;

    public User(String name) {
        this.name = name;
        this.tweets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void tweet(String text) {
        tweets.add(new Tweet(this, text));
    }

    public void retweet(Tweet tweet) {
        tweets.add(tweet);
        tweet.getRetweets().add(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

