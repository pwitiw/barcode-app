import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrdersComponent} from './components/orders/orders.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuardService} from './auth/auth-guard.service';
import {HomeComponent} from "./components/home/home.component";
import {RoutesComponent} from "./components/routes/routes.component";

const routes: Routes = [
    {
        path: 'orders',
        component: OrdersComponent,
    },
    {
        path: 'home',
        component: HomeComponent,
    },
    {
        path: 'routes',
        component: RoutesComponent,
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '**',
        redirectTo: 'home',
        pathMatch: 'full'
    },
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, {useHash: true})
    ],
    exports: [
        RouterModule
    ],
    declarations: []
})
export class AppRoutingModule {
}
