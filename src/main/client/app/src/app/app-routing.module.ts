import {RouterModule, Routes} from "@angular/router";
import {StartComponent} from "./components/start/start.component";
import {OrdersComponent} from "./components/orders/orders.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {NgModule} from "@angular/core";
import {MainPageComponent} from "./components/main-page/main-page.component";

const appRoutingModule: Routes = [
  { path: 'start', component: StartComponent },
  //TODO mudim moze zamiast main to jakies home czy cos (ale to jest takie bardziej czepianie sie)
  { path: 'main', component: MainPageComponent },
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
