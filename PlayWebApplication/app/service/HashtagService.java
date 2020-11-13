package service;

import models.Tweet;
import twitter4j.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

/**
 * handles the hashtag search
 * @auhtor Negin Mohammahzadeh
 *
 */
public class HashtagService {

    /**
     * makes the result async
     * @param keyWords
     * @return
     * @auhtor Negin Mohammahzadeh
     */
    public CompletableFuture<List<Tweet>> getHashtagAsync(String keyWords) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.searchByHashTag(keyWords);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * gets the latest 10 tweet of a specific hashtag
     * @param hashTag
     * @return
     * @throws TwitterException
     * @auhtor Negin Mohammahzadeh
     */
    public  List<Tweet> searchByHashTag(String hashTag) throws TwitterException {

        Twitter twitter = new TwitterFactory().getInstance();
        QueryResult result = twitter.search(new Query("#"+hashTag));


        List<Tweet> tweetList = result.getTweets().stream()
                .limit(10)
                .sorted()
                .map(h -> new Tweet(h.getUser().getName(), h.getText(),
                        Arrays.stream(h.getHashtagEntities()).map(HashtagEntity::getText).collect(toList())))
                .collect(toList());

        return tweetList;
    }






}

