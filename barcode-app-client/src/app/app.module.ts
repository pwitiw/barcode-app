import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {OrderComponent} from './components/orders/orders-list/order.component';
import {OrdersComponent} from './components/orders/orders.component';
import {AppRoutingModule} from './app-routing.module';
import {MainComponent} from './components/main/main.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {OrderRestService} from './components/orders/order.rest.service';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {LoginComponent} from './components/login/login.component';
import {AuthGuardService} from './auth/auth-guard.service';
import {AuthService} from './auth/auth.service';
import {FormsModule} from '@angular/forms';
import {JwtInterceptor} from './auth/jwt.interceptor';
import {OrderDetailComponent} from './components/orders/order-detail/order-detail.component';
import {FrontListComponent} from './components/orders/order-detail/front-list/front-list.component';
import {FrontDetailComponent} from './components/orders/order-detail/front-detail/front-detail.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SearchCriteriaComponent} from './components/orders/search-criteria/search-criteria.component';
import {RestService} from "./services/rest.service";

@NgModule({
  declarations: [
    AppComponent,
    OrderComponent,
    OrdersComponent,
    MainComponent,
    NavbarComponent,
    LoginComponent,
    OrderDetailComponent,
    FrontListComponent,
    FrontDetailComponent,
    SearchCriteriaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    OrderRestService, AuthService, AuthGuardService, RestService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
