import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { StartComponent } from './components/start/start.component';
import { OrdersComponent } from './components/orders/orders.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AppRoutingModule } from "./app-routing.module";
import { MainPageComponent } from './components/main-page/main-page.component';
import { ComponentTableComponent } from './components/component-table/component-table.component';
import { HeaderComponent } from './components/header/header.component';
import {NgxSmartModalModule, NgxSmartModalService} from "ngx-smart-modal";
import { PrintModalComponent } from './components/orders/print-modal/print-modal.component';


@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    OrdersComponent,
    PageNotFoundComponent,
    MainPageComponent,
    ComponentTableComponent,
    HeaderComponent,
    PrintModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxSmartModalModule.forRoot()
  ],
  providers: [ NgxSmartModalService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
