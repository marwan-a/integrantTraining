import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Tweet } from '../model/tweet';
import { Observable } from 'rxjs/Observable';
 
@Injectable()
export class TweetService {
 
  private tweetsUrl: string;
 
  constructor(private http: HttpClient) {
    this.tweetsUrl = 'http://localhost:8080/tweets';
  }
 
  public findAll(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.tweetsUrl);
  }
}