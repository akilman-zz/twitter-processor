package blog.examples;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    private static Stream<String> filteredWordStream(Status status) {
        return normalizedWordStream(status.getText()).filter(wordFilter);
    }

    private static Stream<String> httpWordStream(Status status) {
        return wordStream(status.getText()).filter(httpFilter);
    }

    private static Stream<String> normalizedWordStream(String text) {
        return wordStream(text).map(s -> s.replaceAll("[^a-zA-Z ]", "").toLowerCase()).filter(String::isEmpty);
    }

    private static Stream<String> wordStream(String text) {
        return Arrays.asList(text.split(" ")).stream();
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

    public List<String> _homeStreamUrls(Stream<Status> statusStream) {
        return statusStream.flatMap(AllTheLambdaz::httpWordStream).collect(Collectors.toList());
    }

    /**
     * Helper to fetch more home stream data, however Twitter's throttling got in the way
     * @param nPages number of pages to fetch
     * @return combined stream of {@link twitter4j.Status}
     */
    private Stream<Status> pagedHomeStream(int nPages) {
        return IntStream.range(1, nPages)
                        .mapToObj(page -> getHomeTimelinePage(page).get())
                        .flatMap(pageStream -> pageStream);
    }

    public Optional<Stream<Status>> getHomeTimelinePage(int page) {

        Stream<Status> statusStream = null;

        try {
            statusStream = twitter.getHomeTimeline(new Paging(page)).stream();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(statusStream);
    }
}
