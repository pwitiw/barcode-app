import {Component, OnInit} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatTableDataSource} from "@angular/material";

@Component({
    selector: 'app-reports',
    templateUrl: './reports.component.html',
    styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
    displayedColumns: string[] = ['select', 'position', 'client', 'weight', 'symbol'];
    dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
    selection = new SelectionModel<PeriodicElement>(true, []);

    positions = [];

    constructor() {
        this.positions.push(
            {"client": "jurex"}
        )
    }

    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.dataSource.data.length;
        return numSelected === numRows;
    }
    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
            this.dataSource.data.forEach(row => this.selection.select(row));
    }

    ngOnInit() {
    }

}

export interface PeriodicElement {
    client: string;
    position: number;
    weight: number;
    symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
    {position: 1, client: 'Hydrogen', weight: 1.0079, symbol: 'H'},
    {position: 2, client: 'Helium', weight: 4.0026, symbol: 'He'},
    {position: 3, client: 'Lithium', weight: 6.941, symbol: 'Li'},
    {position: 4, client: 'Beryllium', weight: 9.0122, symbol: 'Be'},
    {position: 5, client: 'Boron', weight: 10.811, symbol: 'B'},
    {position: 6, client: 'Carbon', weight: 12.0107, symbol: 'C'},
    {position: 7, client: 'Nitrogen', weight: 14.0067, symbol: 'N'}
];


