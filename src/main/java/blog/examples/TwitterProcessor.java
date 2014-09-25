package blog.examples;

import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Generic interface the "Twitter Processor" with several contrived methods for illustration
 */
public interface TwitterProcessor {

    Map<Integer, List<String>> homeStreamSizeVsWord() throws TwitterException;

    Map<Integer, List<String>> parallelHomeStreamSizeVsWord() throws TwitterException;

    List<String> homeStreamUrls() throws TwitterException;

    List<String> parallelHomeStreamUrls() throws TwitterException;

    List<Status> getInterestingTimelineTweets_v1() throws TwitterException;

    List<Status> getInterestingTimelineTweets_v2() throws TwitterException;
}
