import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LabelWithInputComponent } from './label-with-input.component';

describe('LabelWithInputComponent', () => {
  let component: LabelWithInputComponent;
  let fixture: ComponentFixture<LabelWithInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LabelWithInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabelWithInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
