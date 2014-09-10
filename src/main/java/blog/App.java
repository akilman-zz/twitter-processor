package blog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet("@AlanTuringYear",
                "Via @Guardian: \"@ScienceMuseum unveils £5m plan for 'world's foremost' #mathematics gallery\" http://bit.ly/1sk8dhf  pic.twitter.com/gEGmITT4ur"));
        tweets.add(new Tweet("@TechCrunch",
                "Canviz Lets You Put Animated GIFs On Your Wall http://tcrn.ch/YxWUHt  by @johnbiggs"));
        tweets.add(new Tweet("@AppDynamics",
                "Drive Huge Revenue Increases, Save Millions in Cost and Reduce Risk: http://bit.ly/1rdAc3m"));

        List<Tweet> mathTweets = tweets.stream().filter(tweet -> tweet.getText().toLowerCase().contains("mathematics")).collect(Collectors.toList());
    }

    static List<Tweet> filterTweetsWithoutLambda(String keyword, List<Tweet> tweets) {

        List<Tweet> filteredList = new ArrayList<>();

        for (Tweet tweet : tweets) {
            if (tweet.getText().toLowerCase().contains(keyword)) {
                filteredList.add(tweet);
                break;
            }
        }

        return filteredList;
    }

    static List<Tweet> filterTweetsWithLambda(String keyword, List<Tweet> tweets) {

        return tweets.stream().filter(
                tweet -> tweet.getText().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
    }
}
