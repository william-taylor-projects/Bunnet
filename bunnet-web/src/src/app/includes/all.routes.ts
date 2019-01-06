import { Routes } from '@angular/router';
import * as Components from './all.components'

export const routes: Routes = [
    { path: 'data/:feed', component: Components.DataFeedComponent },
    { path: 'home', component: Components.HomeComponent },
    { path: '**', redirectTo: 'data/ref', pathMatch: 'full' }
  ];