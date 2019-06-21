import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderRow } from './order-row.component';

describe('OrderRow', () => {
  let component: OrderRow;
  let fixture: ComponentFixture<OrderRow>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderRow ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderRow);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
