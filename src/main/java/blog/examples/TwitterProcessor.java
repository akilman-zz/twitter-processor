package blog.examples;

import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;

/**
 * Generic interface the "Twitter Processor" with several contrived methods for illustration
 */
public interface TwitterProcessor {

    Map<Integer, List<String>> homeStreamSizeVsWord() throws TwitterException;

    Map<Integer, List<String>> parallelHomeStreamSizeVsWord() throws TwitterException;

    List<String> homeStreamUrls() throws TwitterException;

    List<String> parallelHomeStreamUrls() throws TwitterException;

}
