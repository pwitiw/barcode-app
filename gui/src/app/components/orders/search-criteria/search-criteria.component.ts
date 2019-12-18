import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SearchCriteria} from "../types/SearchCriteria";
import {Stage} from "../types/Stage";
import {StageService} from "../stage.service";

@Component({
    selector: 'search-criteria',
    templateUrl: './search-criteria.component.html'
})
export class SearchCriteriaComponent implements OnInit {

    @Output() searchClicked = new EventEmitter<SearchCriteria>();
    completed: boolean;
    orderName: string;
    stage: string;
    customer: string;
    stages: Stage[];
    searchCriteria: SearchCriteria = {};

    constructor(private stageProviderService: StageService) {
        this.stages = stageProviderService.getStages();
    }

    ngOnInit() {
    }

    search() {
        const searchParams: SearchCriteria = {
            name: this.orderName,
            completed: this.completed,
            stage: this.stage,
            customer: this.customer
        };
        this.searchClicked.emit(searchParams);
    }
}
