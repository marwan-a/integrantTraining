package com.javatpoint.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="twittertags")
public class TweetTag {
	@Id
    @GeneratedValue
	@Column(nullable = false, unique = true,name="tweet_tag_id")
	private int tweet_tag_id;
	@Column(nullable = false,name="tweet_tag_name")
	private String tweetTagName;
	
    @ManyToMany(fetch = FetchType.EAGER,
    cascade =
    {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    },
    targetEntity = Tweet.class)
    @JoinTable(name = "tweets_tags",
    inverseJoinColumns = @JoinColumn(name = "tweet_id",
            nullable = false,
            updatable = false),
    joinColumns = @JoinColumn(name = "tweet_tag_id",
            nullable = false,
            updatable = false),
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT),
    inverseForeignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Collection<Tweet> tweets;
    
    public void addTweet(Tweet t)
    {
    	tweets.add(t);
    }
}
