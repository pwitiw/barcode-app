import {Injectable} from "@angular/core";
import {LocalStorageService} from "./local-storage.service";

@Injectable()
export class LoggedUserService {

    private loggedUser: string;

    constructor(private localStorageService: LocalStorageService) {
        this.loggedUser = this.localStorageService.getStoredUser();
    }

    logout() {
        this.loggedUser = null;
        this.localStorageService.clear();
    }

    getLoggedUser(): string {
        return this.loggedUser;
    }

    login(username, token) {
        this.localStorageService.storeUser(username, token);
        this.loggedUser = username;
    }
}

