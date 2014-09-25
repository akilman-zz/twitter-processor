package blog.examples;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * {@link TwitterProcessor} leveraging primarily lambdas for illustration
 */
public class AllTheLambdaz extends ATwitterProcessor {

    /**
     * General string filter to extract empty strings and links
     */
    private static final Predicate<String> wordFilter = s -> !s.equals("") && !s.startsWith("http");
    private static final Predicate<String> httpFilter = s -> s.startsWith("http");

    public AllTheLambdaz(Twitter twitter) {
       super(twitter);
    }

    @Override
    public Map<Integer, List<String>> homeStreamSizeVsWord() throws TwitterException {
        Stream<Status> statusStream = twitter.getHomeTimeline().stream();
        return _homeStreamSizeVsWord(statusStream);

    }

    @Override
    public Map<Integer, List<String>> parallelHomeStreamSizeVsWord() throws TwitterException {
        Stream<Status> statusStream = twitter.getHomeTimeline().stream().parallel();
        return _homeStreamSizeVsWord(statusStream);

    }

    public Map<Integer, List<String>> _homeStreamSizeVsWord(Stream<Status> statusStream) {
        return filteredWordStream(statusStream).collect(Collectors.groupingBy(String::length));
    }

    private static Stream<String> filteredWordStream(Stream<Status> statusStream) {
        return statusStream.flatMap(AllTheLambdaz::filteredWordStream);
    }

    protected static Stream<String> filteredWordStream(Status status) {
        return normalizedWordStream(status.getText()).filter(wordFilter);
    }

    protected static Stream<String> httpWordStream(Status status) {
        return wordStream(status.getText()).filter(httpFilter);
    }

    protected static Stream<String> normalizedWordStream(String text) {
        return wordStream(text).map(s -> s.replaceAll("[^a-zA-Z ]", "").toLowerCase());
    }

    protected static Stream<String> wordStream(String text) {
        return Arrays.asList(text.split(" ")).stream();
    }

    /**
     * Helper to replicate streams for debugging. Clearly this isn't suitable for large streams.
     *
     * @param stream input stream
     * @param <T> type of stream
     * @return replicated stream
     */
    protected static <T> Stream<T> replicateAndLogStream(Stream<T> stream) {
        List<T> ts = stream.collect(toList());
        System.out.println(ts);
        return ts.stream();
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



    @Override
    public List<Status> getInterestingTimelineTweets_v1() throws TwitterException {
        return twitter.getHomeTimeline().stream()
                    .filter(s -> s.getFavoriteCount() > 1 &&
                            s.getText().contains("awesome"))
                    .collect(toList());
    }

    @Override
    public List<Status> getInterestingTimelineTweets_v2() throws TwitterException {
        return twitter.getHomeTimeline().stream()
                .filter(s -> s.getFavoriteCount() > 5 &&
                        s.getText().contains("epic") &&
                        !s.getText().contains("awesome"))
                .collect(toList());
    }

    public List<Status> getInterestingTimelineTweets_v3(Predicate<Status> predicate) throws TwitterException {
        return twitter.getHomeTimeline().stream().filter(predicate).collect(toList());
    }

    public List<String> _homeStreamUrls(Stream<Status> statusStream) {
        return statusStream.flatMap(AllTheLambdaz::httpWordStream).collect(toList());
    }
}
