import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrivilegeListComponent } from './privilege-list.component';

describe('PrivilegeListComponent', () => {
  let component: PrivilegeListComponent;
  let fixture: ComponentFixture<PrivilegeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrivilegeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrivilegeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
