import {Injectable} from "@angular/core";
import {MatPaginatorIntl} from "@angular/material/paginator";

@Injectable()
export class PolishPaginator extends MatPaginatorIntl {
    itemsPerPageLabel = 'Pozycji na stronie';
    nextPageLabel = 'Następna strona';
    previousPageLabel = 'Poprzednia strona';

    getRangeLabel = (page: number, pageSize: number, length: number) => {
        const start = page * pageSize + 1;
        const end = Math.min(page * pageSize + pageSize, length);
        return `${start} - ${end} z ${length}`;
    }
}