

import { Injectable } from '@angular/core'
import { Http } from '@angular/http';
import { MetaService } from './meta.service';

@Injectable()
export class DataService {
    constructor(private meta: MetaService) {

    }
}