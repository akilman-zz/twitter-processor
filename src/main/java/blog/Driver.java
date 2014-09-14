package blog;

import blog.bootstrap.Bootstrap;
import blog.examples.AllTheLambdaz;
import blog.examples.Traditional;
import twitter4j.Twitter;

public class Driver {

    public static void main(String[] args) throws Exception {

        Twitter twitter = Bootstrap.getTwitterInstance();

        AllTheLambdaz lambdaz = new AllTheLambdaz(twitter);
        System.out.println(lambdaz.homeStreamSizeVsWord());
    }

}
