import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './auth/auth-guard.service';
import { JwtInterceptor } from './auth/jwt.interceptor';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RestService } from "./services/rest.service";
import { OrdersModule } from "./components/orders/orders.module";
import { NgxLoadingModule } from "ngx-loading";
import { CommonsModule } from "./components/commons/commons.module";
import { MAT_DATE_LOCALE, MAT_SNACK_BAR_DEFAULT_OPTIONS, MatButtonModule, MatSnackBarModule } from "@angular/material";
import { LocalStorageService } from "./services/local-storage.service";
import { LoggedUserService } from "./services/logged-user.service";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { SnackBarService } from "./services/snack-bar.service";
import { RoutesModule } from "./components/routes/routes.module";
import { MatMenuModule } from "@angular/material/menu";
import { MatIconModule } from "@angular/material/icon";
import { MatTableModule } from "@angular/material/table";
import { MatPaginatorIntl, MatPaginatorModule } from "@angular/material/paginator";
import { HomeModule } from "./components/home/home.module";
import { PolishPaginator } from "./services/polish-paginator.service";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        LoginComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserAnimationsModule,
        BrowserModule,
        CommonsModule,
        HomeModule,
        HttpClientModule,
        MatButtonModule,
        MatIconModule,
        MatMenuModule,
        MatPaginatorModule,
        MatSnackBarModule,
        MatTableModule,
        NgbModule,
        NgxLoadingModule,
        OrdersModule,
        RoutesModule
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: MAT_DATE_LOCALE, useValue: 'pl-PL' },
        {
            provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
            useValue: {
                duration: 2000,
                verticalPosition: 'bottom',
                horizontalPosition: 'end',
            }
        },
        { provide: MatPaginatorIntl, useClass: PolishPaginator },
        AuthGuardService,
        LoggedUserService,
        LocalStorageService,
        PolishPaginator,
        RestService,
        SnackBarService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
