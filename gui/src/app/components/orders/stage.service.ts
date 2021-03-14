import {Injectable} from "@angular/core";
import {Stage} from "./types/Stage";

@Injectable()
export class StageService {

    static INIT = new Stage("INIT", "-Nie rozpoczęto-");
    static MILLING = new Stage("MILLING", "Grabownica");
    static POLISHING = new Stage("POLISHING", "Czyszczenie");
    static BASE = new Stage("BASE", "Podkładowanie");
    static GRINDING = new Stage("GRINDING", "Szlifowanie");
    static PAINTING = new Stage("PAINTING", "Lakierowanie");
    static PACKING = new Stage("PACKING", "Pakowanie");
    static IN_DELIVERY = new Stage("IN_DELIVERY", "W dostarczeniu");
    static DELIVERED = new Stage("DELIVERED", "Dostarczono");

    getStages(): Stage[] {
        return [
            StageService.INIT,
            StageService.MILLING,
            StageService.POLISHING,
            StageService.BASE,
            StageService.GRINDING,
            StageService.PAINTING,
            StageService.PACKING,
            StageService.IN_DELIVERY,
        ]
    }

    convertToView(value: string): string {
        const stage = this.getStages().filter(stage => stage.value == value)[0];
        return stage ? stage.viewValue : "";
    }
}