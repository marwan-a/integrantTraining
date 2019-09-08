import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Tweet } from '../model/tweet';
import { TwitterKafkaInfo } from '../model/twitter-kafka-info';
import { Observable } from 'rxjs/Observable';
 
@Injectable()
export class TweetService {
 
  private tweetsUrl: string;
  private tweetsKafka: string;
 
  constructor(private http: HttpClient) {
    this.tweetsUrl = 'http://localhost:8080/tweets';
    this.tweetsKafka = 'http://localhost:8080/tweets/startTweetFetching';
  }
 
  public findAll(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.tweetsUrl);
  }
  public save(TwitterKafkaInfo: TwitterKafkaInfo) {
    return this.http.post<TwitterKafkaInfo>(this.tweetsKafka, TwitterKafkaInfo);
  }
}