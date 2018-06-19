import {Component, Input, OnInit} from '@angular/core';
//TODO mudim uzywajmy pojedynczych ' ' do importu
import {Order} from "../../models/Order";

@Component({
  selector: 'app-component-table',
  templateUrl: './component-table.component.html',
  styleUrls: ['./component-table.component.scss']
})
//TODO mudim  nazwę możnaby zmienić, bo jest maslo maslane + bardziej generycznie, ze np przekazujemy listę headerów oraz content,
// bo w tym momencie np jakbysmy chcieli miec tabele z pracownikami, to musimy calkowicie nowy komponent tworzyc, a tak
// to moglibys wykorzystac juz istniejacy
export class ComponentTableComponent implements OnInit {

  @Input() order: Order;
  constructor() { }

  ngOnInit() {
  }

}
