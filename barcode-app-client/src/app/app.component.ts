import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  showNavbar: boolean = false;

  onActivate(componentRef){
    this.showNavbar = componentRef.isNavbar();
  }

  ngOnInit() {
    this.showNavbar = false;
  }
}
