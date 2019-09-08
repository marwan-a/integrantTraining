import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TweetService } from '../service/tweet-service.service';
import { TwitterKafkaInfo } from '../model/twitter-kafka-info';
 
@Component({
  selector: 'app-twitter-form',
  templateUrl: './twitter-form.component.html',
  styleUrls: ['./twitter-form.component.css']
})
export class TwitterFormComponent {
 
  TwitterKafkaInfo: TwitterKafkaInfo;
 
  constructor(private route: ActivatedRoute, private router: Router, private tweetService: TweetService) {
    this.TwitterKafkaInfo = new TwitterKafkaInfo();
  }
 
  onSubmit() {
    this.tweetService.save(this.TwitterKafkaInfo).subscribe(result => this.gotoTweetList());
  }
 
  gotoTweetList() {
    this.router.navigate(['/tweets']);
  }
}