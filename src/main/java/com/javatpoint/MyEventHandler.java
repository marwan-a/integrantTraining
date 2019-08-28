package com.javatpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MyEventHandler implements MyEventListener {
	 private final File f=new File("test.txt");
	@Override
	public void onMyEvent(TwitterEvent twitterEvent) throws SQLException {
		System.out.println("tweet recieved");
		String myDriver = "com.mysql.cj.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/tweets";
	      try {
			Class.forName(myDriver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      Connection conn = DriverManager.getConnection(myUrl, "root", "marwan");
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
                conn.close();
            } catch (IOException  e) {
                e.printStackTrace();
            }
        }
	}


}
