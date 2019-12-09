import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SearchCriteria} from "../types/SearchCriteria";
import {Stage} from "../types/Stage";
import {StageService} from "../stage.service";
import {MatOptionSelectionChange} from "@angular/material";

@Component({
    selector: 'search-criteria',
    templateUrl: './search-criteria.component.html'
})
export class SearchCriteriaComponent implements OnInit {

    @Output() searchClicked = new EventEmitter<SearchCriteria>();
    completed: boolean;
    orderName: string;
    stage: string;
    stages: Stage[];

    constructor(private stageProviderService: StageService) {
        this.stages = stageProviderService.getStages();
    }

    ngOnInit() {
    }

    onCompletedChange(event) {
        this.completed = event.checked;
    }

    onStageChange(event: MatOptionSelectionChange): void {
        this.stage = event.source.value;
    }

    onOrderNameChange(event) {
        this.orderName = event.currentTarget.value;
    }


    search() {
        const searchParams: SearchCriteria = {
            name: this.orderName,
            completed: this.completed,
            stage: this.stage
        };
        this.searchClicked.emit(searchParams);
    }
}
