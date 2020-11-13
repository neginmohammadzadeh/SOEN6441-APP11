package controllers;

import models.Tweet;
import models.Profile;
import service.HashtagService;
import service.TweetService;
import service.WordsFrequencyService;
import twitter4j.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import scala.jdk.javaapi.CollectionConverters;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

public class HomeController extends Controller {


    private WordsFrequencyService wordsFrequencyService;
    private TweetService tweetService;
    private HashtagService hashtagService;

    public HomeController() throws TwitterException {
        tweetService = new TweetService();
        hashtagService= new HashtagService();
        wordsFrequencyService =new WordsFrequencyService();
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> tweetSearch(String keyWords) throws Exception {

        CompletableFuture<List<Tweet>> futureTweetList = tweetService.getTweetsAsync(keyWords);

//         CompletionStage<Result> futureResult = futureTweetList.thenApplyAsync( pi -> ok(views.html.tweet.render(pi)));
        return futureTweetList.thenApplyAsync( result-> ok(views.html.tweet.render(result)));
    }

    /**
     * redirects user to a single page of 10 latest tweets of the selected hashtag
     * @author Negin Mohammadzadeh
     * @param keyWords
     * @return
     * @throws Exception
     */
    public CompletionStage<Result> hashtag(String keyWords) throws Exception {

        CompletionStage<List<Tweet>> futureHashtagList = hashtagService.getHashtagAsync("#"+keyWords);

        return futureHashtagList.thenApplyAsync( pi -> ok(views.html.hashtagResults.render(pi)));
    }

    /**
     * @param keywords search term got through url parameter
     * @return each unique word frequency in descending order
     */
    public CompletionStage<Result> frequency(String keywords){
        return  CompletableFuture.supplyAsync(()-> Results.ok(views.html.frequency.render(keywords,CollectionConverters.asScala(wordsFrequencyService.countFrequency(keywords).iterator()).toSeq())));
    }


    public Result index() {

        return ok(views.html.index.render());
    }


     public Result profile(String user) { 
        Profile userProfile = tweetService.getProfile(user);
         return ok(views.html.profile.render(userProfile));
     }
    public Result explore() {
        return ok("hello");
    }


}
