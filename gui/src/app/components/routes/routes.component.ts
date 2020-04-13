import {Component, OnInit} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatTableDataSource} from "@angular/material";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {
    displayedColumns: string[] = ['select', 'client', 'order', 'amount', 'payment', 'sequence'];
    dataSource = new MatTableDataSource<RouteReportDetails>(ELEMENT_DATA);
    selection = new SelectionModel<RouteReportDetails>(true, []);
    movies = [
        'Episode I - The Phantom Menace',
        'Episode II - Attack of the Clones',
        'Episode III - Revenge of the Sith',
        'Episode IV - A New Hope',
        'Episode V - The Empire Strikes Back',
        'Episode VI - Return of the Jedi',
        'Episode VII - The Force Awakens',
        'Episode VIII - The Last Jedi',
        'Episode IX – The Rise of Skywalker'
    ];

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.movies, event.previousIndex, event.currentIndex);
    }


    constructor() {
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

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    ngOnInit() {
    }

}

export interface RouteReportDetails {
    client: string;
    order: string;
    amount: number;
    payment: string;
    sequence: string;
}

const ELEMENT_DATA: RouteReportDetails[] = [
    {client: 'Hydrogen', order: '12-10szt', amount: 1450, payment: 'FV', sequence: ''},
    {client: 'Helium', order: '12-10szt', amount: 627, payment: 'KP', sequence: ''},
    {client: 'Lithium', order: '12-10szt', amount: 1300, payment: 'KP', sequence: ''},
    {client: 'Beryllium', order: '12-10szt', amount: 100, payment: 'FV', sequence: ''},
    {client: 'Boron', order: '12-10szt', amount: 5000, payment: 'FV', sequence: ''},
    {client: 'Carbon', order: '12-10szt', amount: 425, payment: '', sequence: ''},
    {client: 'Nitrogen', order: '12-10szt', amount: 0, payment: '', sequence: ''}
];


