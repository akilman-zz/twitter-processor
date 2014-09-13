package blog;

import blog.bootstrap.Bootstrap;
import blog.examples.AllTheLambdaz;
import blog.examples.Traditional;
import twitter4j.Twitter;

public class Driver {

    public static void main(String[] args) throws Exception {

        Twitter twitter = Bootstrap.getTwitterInstance();

        AllTheLambdaz lambdaz = new AllTheLambdaz(twitter);
        Traditional traditional = new Traditional(twitter);

        System.out.println(lambdaz.homeStreamSizeVsWord());
        System.out.println(traditional.homeStreamSizeVsWord());

        System.out.println(lambdaz.parallelHomeStreamSizeVsWord());

    }

}
