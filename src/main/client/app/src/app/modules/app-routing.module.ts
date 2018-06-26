import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import { StartComponent } from "app/components/start/start.component";
import { HomeComponent } from "app/components/home/home.component";
import { OrdersComponent } from "app/components/orders/orders.component";
import { PageNotFoundComponent } from "app/components/page-not-found/page-not-found.component";

const appRoutingModule: Routes = [
  { path: 'start', component: StartComponent },
  { path: 'home', component: HomeComponent },
  { path: 'orders',        component: OrdersComponent },
  { path: '',   redirectTo: '/start', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutingModule)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
