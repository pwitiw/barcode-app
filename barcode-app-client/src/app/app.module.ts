import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { StartComponent } from './components/start/start.component';
import { HomeComponent } from './components/home/home.component';
import { OrderModule } from './modules/order.module';
import { SharedModule } from 'app/modules/shared.module';
import { OrdersService } from './services/orders/orders.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { AuthenticationService } from './services/authentiaction/authentication.service';
import { UserService } from './services/user/user.service';
import { AuthGuard } from './security/AuthGuard';
import { JwtInterceptor } from './security/JwtInterceptor';
// import { fakeBackendProvider } from 'app/security/FakeBackendInterceptor';

@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    OrderModule,
    SharedModule,
    HttpClientModule
  ],
  providers: [ 
    OrdersService, 
    AuthenticationService, 
    UserService, 
    AuthGuard, 
    // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
