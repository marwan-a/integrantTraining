package com.javatpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.MysqlDataSource;

@Component
public class MyEventHandler {
	 private final File f=new File("test.txt");
	@Async
	@EventListener
	public void onEvent(TwitterEvent twitterEvent) {
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
		      String myUrl = "jdbc:mysql://localhost:3306/tweets";
		      try {
				Class.forName(myDriver);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      Connection conn;
			try {
				conn = DriverManager.getConnection(myUrl, "root", "marwan");
				// the mysql insert statement
			      String query = " insert into tweets (tweet_id, tweet_text, sentiment_score)"
			        + " values (?, ?, ?)";

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString (1, twitterEvent.getTweet_id());
			      preparedStmt.setString (2, twitterEvent.getTweet_text());
			      preparedStmt.setDouble(3, twitterEvent.getSentiment_score());

			      // execute the preparedstatement
			      preparedStmt.execute();
	                conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FileWriter fr = null;
	        try {
	            fr = new FileWriter(f,true);
	            fr.write("tweet recieved"+System.getProperty("line.separator"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            //close resources
	            try {
	                fr.close();
	            } catch (IOException  e) {
	                e.printStackTrace();
	            }
	        }
		}
	}


}
