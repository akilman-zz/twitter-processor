package blog;

import blog.bootstrap.Bootstrap;
import blog.examples.AllTheLambdaz;
import blog.examples.Traditional;
import blog.examples.TraditionalPredicate;
import twitter4j.Status;
import twitter4j.Twitter;

public class Driver {

    public static void main(String[] args) throws Exception {

        Twitter twitter = Bootstrap.getTwitterInstance();

        AllTheLambdaz lambdaz = new AllTheLambdaz(twitter);
        lambdaz.getInterestingTimelineTweets_v3(s -> s.getFavoriteCount() > 42);

        Traditional traditional = new Traditional(twitter);
        traditional.getInterestingTimelineTweets_v3(new TraditionalPredicate() {
            @Override
            public boolean test(Status s) {
                return s.getFavoriteCount() > 42;
            }
        });
    }

}
