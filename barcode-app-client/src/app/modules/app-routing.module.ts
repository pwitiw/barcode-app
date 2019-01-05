import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import { StartComponent } from "app/components/start/start.component";
import { HomeComponent } from "app/components/home/home.component";
import { OrdersComponent } from "app/components/orders/orders.component";
import { PageNotFoundComponent } from "app/components/page-not-found/page-not-found.component";
import { AuthGuard } from "../security/AuthGuard";
import { LoginComponent } from "app/components/login/login.component";
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/services/authentiaction/authentication.service';
import { Injectable } from '@angular/core';


const appRoutingModule: Routes = [
  { path: 'start',   component: StartComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'orders', component: OrdersComponent, canActivate: [AuthGuard]},
  { path: '**', component: PageNotFoundComponent }
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutingModule, {useHash: true})
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}

