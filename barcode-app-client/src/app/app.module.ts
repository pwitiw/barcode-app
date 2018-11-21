import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {StartComponent} from './components/start/start.component';
import {OrdersComponent} from './components/orders/orders.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {AppRoutingModule} from "./app-routing.module";
import {MainPageComponent} from './components/main-page/main-page.component';
import {ComponentTableComponent} from './components/component-table/component-table.component';
import {HeaderComponent} from './components/header/header.component';
import {NgxSmartModalModule, NgxSmartModalService} from "ngx-smart-modal";
import {PrintModalComponent} from './components/orders/print-modal/print-modal.component';
import {TextInputComponent} from './components/text-input/text-input.component';

//TODO mudim moim skromnym zdaniem powinnismy wydzielic takie moduly jak sa sa zakladki: czyli u nas na razie tylko home i order,
// plus jakies common zeby trzymac tam komponenty ktore beda reuzywalne (np tabela, modal) no i moznaby trzymac modele w modulach,
// w ktorych sie ich uzywa
@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    OrdersComponent,
    PageNotFoundComponent,
    MainPageComponent,
    ComponentTableComponent,
    HeaderComponent,
    PrintModalComponent,
    TextInputComponent
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
