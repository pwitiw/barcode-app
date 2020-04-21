import {Injectable} from "@angular/core";
import {MatPaginatorIntl} from "@angular/material/paginator";

@Injectable()
export class PolishPaginator extends MatPaginatorIntl {
    itemsPerPageLabel = 'Pozycji na stronie';
    nextPageLabel = 'Następna strona';
    previousPageLabel = 'Poprzednia strona';
}