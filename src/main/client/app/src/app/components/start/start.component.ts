import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.scss']
})
export class StartComponent implements OnInit {

  myDate: Date;
  day: String;
  month: String;

  days: String[] = ['Niedziela','Poniedziałek','Wtorek','Środa','Czwartek','Piątek','Sobota'];
  months: String[] = ['Styczeń','Luty','Marzec','Kwiecień','Maj','Czerwiec','Lipiec','Sierpień','Wrzesień','Październik','Listopad','Grudzień'];

  constructor() { }

  ngOnInit() {
    this.updateTime();
  }

  updateTime(): void {
    setInterval(() => {
      this.myDate = new Date();
      this.day = this.days[ this.myDate.getDay() ];
      this.month = this.months[ this.myDate.getMonth() ];
    }, 1000);
  }

}
