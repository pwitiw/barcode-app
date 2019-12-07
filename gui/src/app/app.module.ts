import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {NavbarComponent} from './components/navbar/navbar.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './components/login/login.component';
import {AuthGuardService} from './auth/auth-guard.service';
import {FormsModule} from '@angular/forms';
import {JwtInterceptor} from './auth/jwt.interceptor';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RestService} from "./services/rest.service";
import {OrdersModule} from "./components/orders/orders.module";
import {NgxLoadingModule} from "ngx-loading";
import {CommonsModule} from "./components/commons/commons.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReportsComponent} from './components/reports/reports.component';
import {MAT_DATE_LOCALE,} from "@angular/material";
import {LocalStorageService} from "./services/local-storage.service";
import {LoggedUserService} from "./services/logged-user.service";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        LoginComponent,
        ReportsComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        NgbModule,
        OrdersModule,
        NgxLoadingModule,
        CommonsModule,
        BrowserAnimationsModule,
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: MAT_DATE_LOCALE, useValue: 'pl-PL'},

        AuthGuardService, RestService, LocalStorageService, LoggedUserService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
