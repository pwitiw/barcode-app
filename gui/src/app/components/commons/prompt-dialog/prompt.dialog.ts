import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
    selector: 'prompt-dialog',
    templateUrl: 'prompt.dialog.html',
})
export class PromptDialog {
    constructor(
        @Inject(MAT_DIALOG_DATA) public text: string,
        private dialogRef: MatDialogRef<PromptDialog>
    ) {
    }

    close(status: boolean): void {
        this.dialogRef.close(status);
    }
}