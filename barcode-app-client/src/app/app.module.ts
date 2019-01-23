import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { OrderComponent } from './components/orders/orders-list/order.component';
import { OrdersComponent } from './components/orders/orders.component';
import { AppRoutingModule } from './utils/app-routing.module';
import { MainComponent } from './components/main/main.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { OrdersService } from './services/orders/orders.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './services/auth-guard/auth-guard.service';
import { AuthService } from './services/auth/auth.service';
import { FormsModule } from '@angular/forms';
import { JwtInterceptor } from './utils/jwt.interceptor';
import { OrderDetailComponent } from './components/orders/order-detail/order-detail.component';
import { ComponentListComponent } from './components/orders/component-list/component-list.component';
import { ComponentDetailComponent } from './components/orders/component-detail/component-detail.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SearchCriteriaComponent } from './components/orders/search-criteria/search-criteria.component';

@NgModule({
  declarations: [
    AppComponent,
    OrderComponent,
    OrdersComponent,
    MainComponent,
    NavbarComponent,
    LoginComponent,
    OrderDetailComponent,
    ComponentListComponent,
    ComponentDetailComponent,
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
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    OrdersService, AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
