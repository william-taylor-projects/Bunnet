
export interface AppMeta {
    name: string;
    version: string;
    environments: string[];
}

export interface DataMeta {
    summary: string;
    date: number;
    size: number;
    feeds: FeedMeta[];
}

export interface FeedMeta {
    name: string;
    regions: string;
    system: string;
    date: number;
    size: number;
}