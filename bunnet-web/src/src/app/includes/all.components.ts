
import { AppComponent } from '../components/app.component';
import { HomeComponent } from '../components/home/home.component';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { DataFeedComponent } from '../components/datafeed/datafeed.component';

export const declarations = [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    DataFeedComponent
]

export const bootstrap = [
    AppComponent
]

export * from '../components/app.component';
export * from '../components/home/home.component';
export * from '../components/navbar/navbar.component';
export * from '../components/datafeed/datafeed.component';