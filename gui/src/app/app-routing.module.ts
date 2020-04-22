import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrdersComponent} from './components/orders/orders.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuardService} from './auth/auth-guard.service';
import {StatisticsComponent} from "./components/statistics/statistics.component";
import {RoutesComponent} from "./components/routes/routes.component";

const routes: Routes = [
    {
        path: 'orders',
        component: OrdersComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'routes',
        component: RoutesComponent
    },
    // {
    //     path: 'home',
    //     component: StatisticsComponent,
    //     canActivate: [AuthGuardService]
    // },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '**',
        redirectTo: '/orders',
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
