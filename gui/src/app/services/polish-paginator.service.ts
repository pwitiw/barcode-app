import {Injectable} from "@angular/core";
import {MatPaginatorIntl} from "@angular/material/paginator";

@Injectable()
export class PolishPaginator extends MatPaginatorIntl {
    public static readonly PAGE_SIZE_OPTIONS = [15, 25, 50, 100];

    itemsPerPageLabel = 'Pozycji na stronie';
    nextPageLabel = 'Następna strona';

    previousPageLabel = 'Poprzednia strona';

    getRangeLabel = (page: number, pageSize: number, length: number) => {
        const start = page * pageSize + 1;
        const end = Math.min(page * pageSize + pageSize, length);
        return `${start} - ${end} z ${length}`;
    };

    public static getIndex(index: number, page: number, size: number): number {
        return size * page + index + 1;
    }
}

export function getIndex(index: number, page: number, size: number): number {
    return size * page + index + 1;
}