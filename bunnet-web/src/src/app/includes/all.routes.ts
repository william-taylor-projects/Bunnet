import { Routes } from '@angular/router';
import * as Components from './all.components'

export const routes: Routes = [
    { path: 'overview', component: Components.OverviewComponent },
    { path: 'triggers', component: Components.TriggersComponent },
    { path: 'health', component: Components.HealthComponent },
    { path: 'data/:feed', component: Components.DataFeedComponent },
    { path: '**', redirectTo: 'overview', pathMatch: 'full' }
  ];