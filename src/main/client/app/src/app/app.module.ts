import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { StartComponent } from './components/start/start.component';
import { HomeComponent } from './components/home/home.component';
import { OrderModule } from './modules/order.module';
import { SharedModule } from 'app/modules/shared.module';
import { Order } from './models/Order';

@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    OrderModule,
    SharedModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
