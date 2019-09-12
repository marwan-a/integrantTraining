package com.javatpoint.models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tweets")
public class Tweet {
	
	@Id
	@Column(nullable = false, unique = true,name="tweet_id")
	private String tweet_id;
	@Column(nullable = false,name="tweet_text")
	private String tweet_text;
	@Column(nullable = false,name="sentiment_score")
	private double sentiment;
	@Column(nullable = false,name="created_at")
	private Date created_at;
	
    @ManyToMany(fetch = FetchType.EAGER,
    targetEntity = TweetTag.class)
    @JoinTable(name = "tweets_tags",
    joinColumns = @JoinColumn(name = "tweet_id",
            nullable = false,
            updatable = false),
    inverseJoinColumns = @JoinColumn(name = "tweet_tag_id",
            nullable = false,
            updatable = false),
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT),
    inverseForeignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Collection<TweetTag> tags;
}
