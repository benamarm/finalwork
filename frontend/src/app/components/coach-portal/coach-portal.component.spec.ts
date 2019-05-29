import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachPortalComponent } from './coach-portal.component';

describe('CoachPortalComponent', () => {
  let component: CoachPortalComponent;
  let fixture: ComponentFixture<CoachPortalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoachPortalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoachPortalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
