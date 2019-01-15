import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrdersComponent } from '../components/orders/orders.component';
import { MainComponent } from '../components/main/main.component';
import { LoginComponent } from '../components/login/login.component';
import { AuthGuardService } from '../services/auth-guard/auth-guard.service';

const routes: Routes = [
    {
      path: 'orders',
      component: OrdersComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'main',
      component: MainComponent,
      canActivate: [AuthGuardService]
    },
    {
      path: 'login',
      component: LoginComponent
    },
    { path: '**',
    redirectTo: '/main',
    pathMatch: 'full' },
];
    // {
    //   path: 'admin',
    //   component: AdminLoginComponent,
    //   children: [
    //     {
    //       path: 'admin-view',
    //       component: AdminViewComponent,
    //       canActivate: [AuthGuardService],
    //       resolve: {
    //         pollList: PollListResolver
    //       }
    //     }
    //   ]
    // },
    // {
    //   path: 'pollView/:pollId',
    //   component: PoolViewComponent,
    //   resolve: {
    //     poll: PollResolver
    //   }
    // },
    
  
  @NgModule({
    imports: [
      RouterModule.forRoot(routes)
    ],
    exports: [
      RouterModule
    ],
    declarations: []
  })
  export class AppRoutingModule { }
  