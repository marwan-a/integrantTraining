import { Component, OnInit ,Input} from '@angular/core';
import { Tweet } from '../model/tweet';
import { TweetService } from '../service/tweet-service.service';
import 'rxjs/add/observable/interval';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Observable';
 
@Component({
  selector: 'app-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.css']
})
export class TweetListComponent implements OnInit {

  @Input() tweets: Observable<any>;
  constructor(private api: TweetService) { }

  ngOnInit() {
    this.tweets = Observable
      .interval(1000)
      .startWith(0).switchMap(() => this.api.findAll());
  }

}