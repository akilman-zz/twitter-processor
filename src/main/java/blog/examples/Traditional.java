package blog.examples;

import blog.TooMuchWorkDontWannaException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.*;

/**
 * {@link TwitterProcessor} using traditional java loop constructs for processing
 */
public class Traditional extends ATwitterProcessor {

    public Traditional(Twitter twitter) {
        super(twitter);
    }

    @Override
    public Map homeStreamSizeVsWord() throws TwitterException {

        List<Status> homeTimeline = twitter.getHomeTimeline();

        List<String> words = new ArrayList<>();
        for (Status status : homeTimeline) {

            String text = status.getText();
            String textWithPunctuationRemoved = text.replaceAll("[^a-zA-Z ]", "");

            List<String> newWords = Arrays.asList(textWithPunctuationRemoved.toLowerCase().split(" "));
            words.addAll(newWords);
        }

        Map<Integer, List<String>> sizeVsWordList = new HashMap<>();
        for (String word : words) {
            Integer length = word.length();

            if (length == 0 || word.startsWith("http")) continue;

            List<String> wordList = sizeVsWordList.get(length);
            if (wordList == null) {
                wordList = new ArrayList<>();
                sizeVsWordList.put(length, wordList);
            }

            wordList.add(word);
        }

        return sizeVsWordList;
    }

    @Override
    public Map parallelHomeStreamSizeVsWord() throws TwitterException {
        throw new TooMuchWorkDontWannaException("not implemented");
    }

    @Override
    public List<String> homeStreamUrls() throws TwitterException {

        List<Status> homeTimeline = twitter.getHomeTimeline();

        List<String> words = new ArrayList<>();
        for (Status status : homeTimeline) {
            List<String> newWords = Arrays.asList(status.getText().toLowerCase().split(" "));
            words.addAll(newWords);
        }

        List<String> httpUrls = new ArrayList<>();
        for (String word : words) {

            if (word.startsWith("http")) {
                httpUrls.add(word);
            }
        }

        return httpUrls;
    }

    @Override
    public List<String> parallelHomeStreamUrls() throws TwitterException {
        throw new TooMuchWorkDontWannaException("not implemented");
    }

    @Override
    public List<Status> getInterestingTimelineTweets_v1() throws TwitterException {

        List<Status> homeTimeLine = twitter.getHomeTimeline();
        List<Status> result = new ArrayList<>();

        for (Status s : homeTimeLine) {
            if (s.getFavoriteCount() > 1 &&
                s.getText().contains("awesome")) {
                result.add(s);
            }
        }

        return result;
    }

    @Override
    public List<Status> getInterestingTimelineTweets_v2() throws TwitterException {

        List<Status> homeTimeLine = twitter.getHomeTimeline();
        List<Status> result = new ArrayList<>();

        for (Status s : homeTimeLine) {
            if (s.getFavoriteCount() > 5 &&
                s.getText().contains("epic") &&
                !s.getText().contains("awesome")) {
                result.add(s);
            }
        }

        return result;
    }

    public List<Status> getInterestingTimelineTweets_v3(TraditionalPredicate predicate) throws TwitterException {

        List<Status> homeTimeLine = twitter.getHomeTimeline();
        List<Status> result = new ArrayList<>();

        for (Status s : homeTimeLine) {
            if (predicate.test(s)) {
                result.add(s);
            }
        }

        return result;
    }
}
