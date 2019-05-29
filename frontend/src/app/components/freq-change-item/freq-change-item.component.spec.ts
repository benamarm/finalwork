import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FreqChangeItemComponent } from './freq-change-item.component';

describe('FreqChangeItemComponent', () => {
  let component: FreqChangeItemComponent;
  let fixture: ComponentFixture<FreqChangeItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FreqChangeItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FreqChangeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
