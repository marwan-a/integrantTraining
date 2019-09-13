package com.javatpoint;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.javatpoint.controllers.TweetController;
import com.javatpoint.models.Tweet;
import com.javatpoint.models.TweetTag;
import com.javatpoint.repositories.TweetRepository;
import com.javatpoint.repositories.TweetTagRepository;

@Component
public class MyEventHandler {
	 @Autowired
	 private TweetRepository tweetRepository;
	 @Autowired
	 private TweetTagRepository tweetTagRepositroy;
	 @Autowired
	 private TweetController tweetController;
	@Async
	@EventListener
	public void onEvent(TwitterEvent twitterEvent) {
		{	String tag=twitterEvent.getTweet_tag();
			TweetTag tweetTag=tweetTagRepositroy.findByTweetTagName(tag);
			if(tweetTag==null)
			{
				tweetTag=new TweetTag();
				tweetTag.setTweetTagName(tag);
				tweetTagRepositroy.save(tweetTag);
			}
			Tweet tweet=new Tweet();
			tweet.setTweet_id(twitterEvent.getTweet_id());
			tweet.setSentiment(twitterEvent.getSentiment_score());
			tweet.setTweet_text(twitterEvent.getTweet_text());
			tweet.setCreated_at(twitterEvent.getCreated_at());
			switch ((int)tweet.getSentiment()) {
			case 0:		
				tweetController.setVeryNegative(tweetController.getVeryNegative()+1);
				break;
			case 1:			
				tweetController.setNegative(tweetController.getNegative()+1);
				break;
			case 2:			
				tweetController.setNeutral(tweetController.getNeutral()+1);
				break;
			case 3:			
				tweetController.setPositive(tweetController.getPositive()+1);
				break;
			case 4:			
				tweetController.setVeryPositive(tweetController.getVeryPositive()+1);
				break;
			default:
				break;
			}
			tweet.setTags(Arrays.asList(tweetTag));
			tweetTag.addTweet(tweet);
			tweetRepository.save(tweet);
		}
	}


}
