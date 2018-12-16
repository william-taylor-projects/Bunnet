
import { Injectable } from '@angular/core'
import { MetaService } from './meta.service'
import { Observable } from 'rxjs';

@Injectable()
export class FeedService {
    constructor(private meta: MetaService) {

    }

    systemFeedAvailable(system: string) {
        return this.meta.data.feeds.find(o => o.name.includes(system) || o.system == system) != null;
    }
}