import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class LoadingService {

  counter: number;


  constructor() {
    this.counter = 0;
  }

  isLoading(): boolean {
    return this.counter > 0;
  }

  show(): void {
    this.counter++;
  }

  hide(): void {
    this.counter--;
  }
}
