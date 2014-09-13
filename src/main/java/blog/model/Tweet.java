package blog.model;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    private final User user;
    private final String text;
    private final List<User> retweets;

    public Tweet(User user, String text) {
        this.user = user;
        this.text = text;
        this.retweets = new ArrayList<>();

        user.getTweets().add(this);
    }

    public Tweet(User user, String text, List<User> retweets) {
        this.user = user;
        this.text = text;
        this.retweets = retweets;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public List<User> getRetweets() {
        return retweets;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tweet{");
        sb.append("user=").append(user);
        sb.append(", text='").append(text).append('\'');
        sb.append(", retweets=").append(retweets);
        sb.append('}');
        return sb.toString();
    }
}
