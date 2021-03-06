import { Component, OnInit ,Input} from '@angular/core';
import { Tweet } from '../model/tweet';
import { TweetService } from '../service/tweet-service.service';
import 'rxjs/add/observable/interval';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Observable';
import { createChart } from '../helperfunction';
@Component({
  selector: 'app-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.css']
})

export class TweetListComponent implements OnInit {

  @Input() tweets: Observable<any>;
  tweetsSentiment: any;
  tweetsCount: any;
  veryNegative:any;
  negative:any;
  neutral:any;
  positive:any;
  veryPositive:any;
  constructor(private api: TweetService) { }

 ngOnInit() {
   var datapoints =new Array(5);
    var previousVeryNeg :number;
    var previousNeg :number;
    var previousNeut :number;
    var previousPos :number;
    var previousVeryPos :number;
    previousVeryNeg=0;
    previousNeg=0;
    previousNeut=0;
    previousPos=0;
    previousVeryPos=0;
    this.tweetsCount=Observable.interval(1000).startWith(0).switchMap(() => this.api.findTweetsCount());
    this.tweetsCount.subscribe((res: any) => {
      this.tweetsCount=res;
    });
    this.tweetsSentiment=Observable.interval(1000).startWith(0).switchMap(() => this.api.findTweetsSentiment());
    this.tweetsSentiment.subscribe((res: any) => {
      this.tweetsSentiment=res;
    });
    this.tweets = Observable
      .interval(1000)
      .startWith(0).switchMap(() => this.api.findAll());
    this.veryNegative=Observable.interval(10000).startWith(0).switchMap(() => this.api.findVeryNegativeTweets());
    this.veryNegative.subscribe((res: any) => {
      this.veryNegative=res;
      datapoints[0]={y:this.veryNegative,label:"Very Negative"};
    });
    this.negative= Observable.interval(10000).startWith(0).switchMap(() => this.api.findNegativeTweets());
    this.negative.subscribe((res: any) => {
      this.negative=res;
      datapoints[1]={y:this.negative,label:"Negative"};
    });
    this.neutral=Observable.interval(10000).startWith(0).switchMap(() => this.api.findNeutralTweets());
    this.neutral.subscribe((res: any) => {
      this.neutral=res;
      datapoints[2]={y:this.neutral,label:"Neutral"};
    });
    this.positive=Observable.interval(10000).startWith(0).switchMap(() => this.api.findPositiveTweets());
    this.positive.subscribe((res: any) => {
      this.positive=res;
      datapoints[3]={y:this.positive,label:"Positive"};
    });
    this.veryPositive=Observable.interval(10000).startWith(0).switchMap(() => this.api.findVeryPositiveTweets());
    this.veryPositive.subscribe((res: any) => {
      this.veryPositive=res;
      datapoints[4]={y:this.veryPositive,label:"Very Positive"};
      if(previousVeryNeg!=this.veryNegative || previousNeg!=this.negative || previousNeut!=this.neutral || previousPos!=this.positive || previousVeryPos!=this.veryPositive)
        {
          createChart(datapoints);
          previousVeryNeg=this.veryNegative;
          previousNeg=this.negative;
          previousNeut=this.neutral;
          previousPos=this.positive;
          previousVeryPos=this.veryPositive;
        }
    });  
  }

}