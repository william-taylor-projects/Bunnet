

import { DataService } from '../services/data.service'
import { MetaService } from '../services/meta.service'
import { FeedService } from '../services/feed.service'

export const providers = [
    DataService,
    FeedService,
    MetaService
]

export * from '../services/data.service'
export * from '../services/meta.service'
export * from '../services/feed.service'