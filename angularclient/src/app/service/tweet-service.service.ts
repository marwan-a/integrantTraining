import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Tweet } from '../model/tweet';
import { TwitterKafkaInfo } from '../model/twitter-kafka-info';
import { Observable } from 'rxjs/Observable';
import {TweetsSentiment} from '../model/tweets-sentiment';
import {TweetsCount} from '../model/tweets-count';
@Injectable()
export class TweetService {
 
  private tweetsUrl: string;
  private tweetsKafka: string;
  private tweetsSentiment: string;
  private tweetsCount: string;
  private tweetsUrlVeryNegative: string;
  private tweetsUrlNegative: string;
  private tweetsUrlNeutral: string;
  private tweetsUrlPositive: string;
  private tweetsUrlVeryPositive: string;
 
  constructor(private http: HttpClient) {
    this.tweetsUrl = 'http://localhost:8080/tweets';
    this.tweetsKafka = 'http://localhost:8080/tweets/startTweetFetching';
    this.tweetsSentiment='http://localhost:8080/tweets/sentiment';
    this.tweetsCount='http://localhost:8080/tweets/count';
    this.tweetsUrlVeryNegative = 'http://localhost:8080/tweets/sentiment/veryNegative';
    this.tweetsUrlNegative = 'http://localhost:8080/tweets/sentiment/negative';
    this.tweetsUrlNeutral = 'http://localhost:8080/tweets/sentiment/neutral';
    this.tweetsUrlPositive = 'http://localhost:8080/tweets/sentiment/positive';
    this.tweetsUrlVeryPositive = 'http://localhost:8080/tweets/sentiment/veryPositive';
  }
 
  public findAll(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.tweetsUrl);
  }
  public save(TwitterKafkaInfo: TwitterKafkaInfo) {
    return this.http.post<TwitterKafkaInfo>(this.tweetsKafka, TwitterKafkaInfo);
  }
  public findTweetsSentiment() :Observable<TweetsSentiment>
  {  
    return this.http.get<TweetsSentiment>(this.tweetsSentiment);
  }
  public findTweetsCount():Observable<TweetsCount>
  { 
    return this.http.get<TweetsCount>(this.tweetsCount);
  }

  public findVeryNegativeTweets(): Observable<number> {
    return this.http.get<number>(this.tweetsUrlVeryNegative);
  }
  public findNegativeTweets(): Observable<number> {
    return this.http.get<number>(this.tweetsUrlNegative);
  }
  public findNeutralTweets(): Observable<number> {
    return this.http.get<number>(this.tweetsUrlNeutral);
  }
  public findPositiveTweets(): Observable<number> {
    return this.http.get<number>(this.tweetsUrlPositive);
  }
  public findVeryPositiveTweets(): Observable<number> {
    return this.http.get<number>(this.tweetsUrlVeryPositive);
  }
}