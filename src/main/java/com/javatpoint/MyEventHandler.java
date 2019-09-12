package com.javatpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.javatpoint.controllers.TweetController;
import com.javatpoint.models.Tweet;
import com.javatpoint.models.TweetTag;
import com.javatpoint.repositories.TweetRepository;
import com.javatpoint.repositories.TweetTagRepository;
import com.mysql.cj.jdbc.MysqlDataSource;

@Component
public class MyEventHandler {
	 private final File f=new File("test.txt");
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
			tweet.setTags(Arrays.asList(tweetTag));
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
			tweetRepository.save(tweet);
			tweetTag.addTweet(tweet);
			tweetTagRepositroy.save(tweetTag);
//			String myDriver = "com.mysql.cj.jdbc.Driver";
//		      String myUrl = "jdbc:mysql://localhost:3306/tweets";
//		      try {
//				Class.forName(myDriver);
//			} catch (ClassNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		      Connection conn;
//			try {
//				conn = DriverManager.getConnection(myUrl, "root", "marwan");
//				// the mysql insert statement
//			      String query = " insert into tweets (tweet_id, tweet_text, sentiment_score,created_at)"
//			        + " values (?, ?, ?,?)";
//
//			      // create the mysql insert preparedstatement
//			      PreparedStatement preparedStmt = conn.prepareStatement(query);
//			      preparedStmt.setString (1, twitterEvent.getTweet_id());
//			      preparedStmt.setString (2, twitterEvent.getTweet_text());
//			      preparedStmt.setDouble(3, twitterEvent.getSentiment_score());
//			      Timestamp ts=new Timestamp(twitterEvent.getCreated_at().getTime());
//			      preparedStmt.setTimestamp(4, ts);
//
//			      // execute the preparedstatement
//			      preparedStmt.execute();
//	                conn.close();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
	}


}
