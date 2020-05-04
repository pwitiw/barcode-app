import {MatSnackBar} from "@angular/material";
import {Injectable} from "@angular/core";

@Injectable()
export class SnackBarService {

    constructor(private snackBar: MatSnackBar) {
    }

    success(message: string, action?: string): void {
        this.snackBar.open(message, action, {duration:100000, panelClass: ["success"]});
    }

    failure(message: string, action?: string) {
        this.snackBar.open(message, action, {panelClass: ["error"]});
    }
}
