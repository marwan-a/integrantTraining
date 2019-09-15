import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PrivilegeListComponent } from './privilege-list/privilege-list.component';
import { TweetListComponent } from './tweet-list/tweet-list.component';
import { TwitterFormComponent } from './twitter-form/twitter-form.component';
import { TweetListTagComponent } from './tweet-list-tag/tweet-list-tag.component';
const routes: Routes = [
  { path: 'privileges', component: PrivilegeListComponent },
  { path: 'tweets', component: TweetListComponent },
  { path: 'startTweetFetching', component: TwitterFormComponent },
  { path: 'tweets/:tag', component: TweetListTagComponent }
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }