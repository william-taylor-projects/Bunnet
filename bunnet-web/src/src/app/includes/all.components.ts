
import { AppComponent } from '../components/app.component';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { OverviewComponent } from '../components/overview/overview.component';
import { TriggersComponent } from '../components/triggers/triggers.component';
import { DataFeedComponent } from '../components/datafeed/datafeed.component';
import { HealthComponent } from '../components/health/health.component';

export const declarations = [
    AppComponent,
    NavbarComponent,
    OverviewComponent,
    TriggersComponent,
    HealthComponent,
    DataFeedComponent
]

export const bootstrap = [
    AppComponent
]


export * from '../components/app.component';
export * from '../components/navbar/navbar.component';
export * from '../components/overview/overview.component';
export * from '../components/triggers/triggers.component';
export * from '../components/datafeed/datafeed.component';
export * from '../components/health/health.component';