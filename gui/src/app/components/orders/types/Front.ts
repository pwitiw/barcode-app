export interface Front {
    barcode: number;
    height: number;
    width: number;
    quantity: number;
    comment: string;
    lastModification: string;
    damaged: boolean;
    stage: string;
    processings: ProcessingDetails[];
    amendments: ProcessingDetails[];

}

interface ProcessingDetails {
    stage: string;
    dateTime: string;
}
