

import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AppMeta, DataMeta } from '../model/meta.model';
import { interval } from 'rxjs';

@Injectable()
export class MetaService {
    public app: AppMeta;
    public data: DataMeta;

    constructor(private http: HttpClient) {
        this.app = { name: '', version: '', environments: [] }
        this.data = { summary: '', date: 0, size: 0, feeds: [] }
        this.fetchMeta();

        interval(10000).subscribe(() => this.fetchMeta())
    }

    fetchMeta() {
        this.fetchAppMeta()
        this.fetchDataMeta()
    }

    fetchAppMeta() {
        /*
        this.http.get<AppMeta>(`${environment.url}/app-meta`).subscribe(meta => {
            this.app = meta;
        });
        */
    }

    fetchDataMeta() {
        /*
        this.http.get<DataMeta>(`${environment.url}/data-meta`).subscribe(meta => {
            this.data = meta;
        });
        */
    }
}