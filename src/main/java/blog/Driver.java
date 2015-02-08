package blog;

import blog.bootstrap.Bootstrap;
import blog.examples.AllTheLambdaz;
import blog.examples.ScalaTwitterProcessor;
import blog.examples.Traditional;
import blog.examples.TraditionalPredicate;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.List;

public class Driver {

    public static void main(String[] args) throws Exception {

        Twitter twitter = Bootstrap.getTwitterInstance();

        AllTheLambdaz lambdaz = new AllTheLambdaz(twitter);
        List<String> javaUrls = lambdaz.homeStreamUrls();
        System.out.println(javaUrls);

        ScalaTwitterProcessor scalaTwitterProcessor = new ScalaTwitterProcessor(twitter);
        List<String> urls = scalaTwitterProcessor.homeStreamUrls();
        System.out.println(urls);


        Traditional traditional = new Traditional(twitter);
        traditional.getInterestingTimelineTweets_v3(new TraditionalPredicate() {
            @Override
            public boolean test(Status s) {
                return s.getFavoriteCount() > 42;
            }
        });
    }

}
