import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.scss']
})
export class StartComponent implements OnInit {

  myDate: Date;

  constructor() { }

  ngOnInit() {
    this.updateTime();
  }

  isNavbar(): boolean {
    return false;
  }

  updateTime(): void {
    setInterval(() => {
      this.myDate = new Date();
    }, 1000);
  }

}
