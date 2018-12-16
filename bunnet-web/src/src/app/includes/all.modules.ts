
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';


import { AgGridModule } from 'ag-grid-angular';
import { LayoutModule } from '@angular/cdk/layout';

import { MatButtonModule, MatCheckboxModule, MatToolbarModule, MatIconModule, MatListModule } from '@angular/material';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material';
import { MatBadgeModule } from '@angular/material/badge';

import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { StoreModule } from '@ngrx/store';

import { routes } from './all.routes';

export const imports = [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatSortModule,
    MatSidenavModule,
    MatButtonModule,
    MatCheckboxModule,
    MatPaginatorModule,
    MatBadgeModule,
    MatInputModule,
    MatTabsModule,
    AgGridModule.withComponents([]),
    MatFormFieldModule,
    LayoutModule,
    MatToolbarModule,
    MatGridListModule,
    MatIconModule,
    MatListModule,
    RouterModule.forRoot(routes),
    StoreModule.forRoot({
        
    }),
    StoreDevtoolsModule.instrument()
]