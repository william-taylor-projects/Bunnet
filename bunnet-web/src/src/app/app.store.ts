
import { AppMeta, DataMeta } from './model/meta.model'

export interface MetaStore {
    app: AppMeta,
    data: DataMeta
}

export interface BunnetStore {
    meta: MetaStore
}