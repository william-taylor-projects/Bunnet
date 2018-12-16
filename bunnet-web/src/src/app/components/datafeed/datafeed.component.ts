import { Component } from '@angular/core';
import { FeedService } from '../../includes/all.services'
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';

@Component({
    selector: 'datafeed',
    templateUrl: './datafeed.component.html',
    styleUrls: ['./datafeed.component.css']
})
export class DataFeedComponent {
    activeFeed: string;
    columnDefs = [
        { headerName: 'Column 1', field: 'col1' },
        { headerName: 'Column 2', field: 'col2' },
        { headerName: 'Column 3', field: 'col3' },
        { headerName: 'Column 4', field: 'col4' },
        { headerName: 'Column 5', field: 'col5' },
        { headerName: 'Column 6', field: 'col6' },
    ];

    value = { col1: 'Value', col2: 'Value', col3: 'Value', col4: 'Value', col5: 'Value', col6: 'Value' };
    rowData = [];

    constructor(private route: ActivatedRoute, private feeds: FeedService, router: Router) {
        router.events.subscribe(val => {
            if (val instanceof NavigationEnd) {
                let feed = this.route.snapshot.paramMap.get('feed');
                if (this.activeFeed != feed) {
                    this.reloadFeedData(feed);
                }
            }
        })

        for (let i = 0; i < 1000; i++) {
            this.rowData.push(this.value);
        }
    }

    reloadFeedData(feed) {
        this.activeFeed = feed;
    }

    disableSystemTab(system: string) {
        return !this.feeds.systemFeedAvailable(system);
    }
}
