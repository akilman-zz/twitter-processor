package blog.examples;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link TwitterProcessor} leveraging primarily lambdas for illustration
 */
public class AllTheLambdaz extends ATwitterProcessor {

    public AllTheLambdaz(Twitter twitter) {
       super(twitter);
    }

    @Override
    public Map homeStreamSizeVsWord() throws TwitterException {

        Stream<Status> statusStream = twitter.getHomeTimeline().stream();
        return _homeStreamSizeVsWord(statusStream);

    }

    @Override
    public Map parallelHomeStreamSizeVsWord() throws TwitterException {

        Stream<Status> statusStream = twitter.getHomeTimeline().stream().parallel();
        return _homeStreamSizeVsWord(statusStream);

    }

    private Map _homeStreamSizeVsWord(Stream<Status> stream) {
        return stream.flatMap((Status s) ->
                    Arrays.asList(s.getText().replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ")).stream())
                    .filter(s -> !s.equals("") && !s.startsWith("http"))
                    .collect(Collectors.groupingBy(String::length));
    }

    @Override
    public List<String> homeStreamUrls() throws TwitterException {

        Stream<Status> statusStream = twitter.getHomeTimeline().stream();
        return _homeStreamUrls(statusStream);

    }

    @Override
    public List<String> parallelHomeStreamUrls() throws TwitterException {

        Stream<Status> statusStream = twitter.getHomeTimeline().stream().parallel();
        return _homeStreamUrls(statusStream);

    }

    private List<String> _homeStreamUrls(Stream<Status> stream) {
        return stream
                .flatMap((Status s) -> Arrays.asList(s.getText().replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ")).stream())
                .filter(s -> !s.equals("") && s.startsWith("http"))
                .collect(Collectors.toList());
    }

}
