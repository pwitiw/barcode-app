/* 3rd party libraries */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule  } from '@angular/forms';
import { HeaderComponent } from 'app/components/header/header.component';
import { PageNotFoundComponent } from 'app/components/page-not-found/page-not-found.component';
import { TableComponent } from 'app/components/table/table.component';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { PrintModalComponent } from 'app/components/print-modal/print-modal.component';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { AppRoutingModule } from 'app/modules/app-routing.module';


@NgModule({
  imports: [
    CommonModule,
    NgxSmartModalModule.forRoot(),
    AppRoutingModule,
  ],
  declarations: [
    HeaderComponent,
    PageNotFoundComponent,
    TableComponent,
    PrintModalComponent,
    NavbarComponent
  ],
  exports: [
    CommonModule,
    HeaderComponent,
    PageNotFoundComponent,
    TableComponent,
    PrintModalComponent,
    NavbarComponent,
    AppRoutingModule
  ]
})
export class SharedModule { }