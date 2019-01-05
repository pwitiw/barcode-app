import {Component, Input, OnInit} from '@angular/core';
import {Order} from '../../models/Order';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
//TODO mudim  nazwę możnaby zmienić, bo jest maslo maslane + bardziej generycznie, ze np przekazujemy listę headerów oraz content,
// bo w tym momencie np jakbysmy chcieli miec tabele z pracownikami, to musimy calkowicie nowy komponent tworzyc, a tak
// to moglibys wykorzystac juz istniejacy
export class TableComponent implements OnInit {

  @Input() headerList: String[];
  @Input() data: any;

  constructor() { }

  getKeysOfDataObject(item: any): String[] {
    return Object.keys(item);
  }
  
  ngOnInit() {
  }

}
