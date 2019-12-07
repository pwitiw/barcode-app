import {Injectable} from "@angular/core";

@Injectable()
export class LocalStorageService {
    static readonly TOKEN_KEY = "token";
    static readonly USERNAME_KEY = "username";

    public clear(): void {
        localStorage.removeItem(LocalStorageService.TOKEN_KEY);
        localStorage.removeItem(LocalStorageService.USERNAME_KEY);
    }

    getStoredUser() {
        return localStorage.getItem(LocalStorageService.USERNAME_KEY)
    }

    storeUser(username: string, token: string) {
        localStorage.setItem(LocalStorageService.TOKEN_KEY, token);
        localStorage.setItem(LocalStorageService.USERNAME_KEY, username);
    }

    getStoredToken() {
        return localStorage.getItem(LocalStorageService.TOKEN_KEY);
    }
}