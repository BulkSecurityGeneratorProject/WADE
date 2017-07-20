import { BaseEntity } from './../../shared';

export class AixmFeature implements BaseEntity {
    constructor(
        public id?: number,
        public identifier?: string,
        public type?: string,
        public timeSlice?: string,
        public geometry?: string,
        public lowerLimit?: number,
        public upperLimit?: number,
    ) {
    }
}
