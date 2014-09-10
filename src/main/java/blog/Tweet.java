package blog;

public class Tweet {

    private final String user;
    private final String text;

    public Tweet(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
}
