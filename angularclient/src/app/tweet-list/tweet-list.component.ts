import { Component, OnInit } from '@angular/core';
import { Tweet } from '../model/tweet';
import { TweetService } from '../service/tweet-service.service';
 
@Component({
  selector: 'app-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.css']
})
export class TweetListComponent implements OnInit {
 
  tweets: Tweet[];
 
  constructor(private tweetService: TweetService) {
  }
 
  ngOnInit() {
    this.tweetService.findAll().subscribe(data => {
      this.tweets = data;
    });
  }
}