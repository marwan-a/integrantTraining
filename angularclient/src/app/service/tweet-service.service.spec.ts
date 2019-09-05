import { TestBed } from '@angular/core/testing';

import { TweetServiceService } from './tweet-service.service';

describe('TweetServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TweetServiceService = TestBed.get(TweetServiceService);
    expect(service).toBeTruthy();
  });
});
