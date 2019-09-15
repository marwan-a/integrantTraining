import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetListTagComponent } from './tweet-list-tag.component';

describe('TweetListTagComponent', () => {
  let component: TweetListTagComponent;
  let fixture: ComponentFixture<TweetListTagComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TweetListTagComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TweetListTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
