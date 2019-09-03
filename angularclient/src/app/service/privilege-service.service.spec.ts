import { TestBed, inject } from '@angular/core/testing';

import { PrivilegeService } from './privilege-service.service';

describe('UserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PrivilegeService]
    });
  });

  it('should be created', inject([PrivilegeService], (service: PrivilegeService) => {
    expect(service).toBeTruthy();
  }));
});
