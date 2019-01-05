/* 3rd party libraries */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule  } from '@angular/forms';
import { HeaderComponent } from 'app/components/header/header.component';
import { PageNotFoundComponent } from 'app/components/page-not-found/page-not-found.component';
import { TableComponent } from 'app/components/table/table.component';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { ModalWindowComponent } from 'app/components/modal-window/modal-window.component';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { AppRoutingModule } from 'app/modules/app-routing.module';
import {LoginComponent} from "../components/login/login.component";


@NgModule({
  imports: [
    CommonModule,
    NgxSmartModalModule.forRoot(),
    AppRoutingModule,
    FormsModule,
  ],
  declarations: [
    HeaderComponent,
    PageNotFoundComponent,
    TableComponent,
    ModalWindowComponent,
    NavbarComponent,
    LoginComponent
  ],
  exports: [
    CommonModule,
    HeaderComponent,
    PageNotFoundComponent,
    TableComponent,
    ModalWindowComponent,
    NavbarComponent,
    AppRoutingModule,
    LoginComponent,
    FormsModule

  ]
})
export class SharedModule { }
