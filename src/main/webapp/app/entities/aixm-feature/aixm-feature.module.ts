import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WadeSharedModule } from '../../shared';
import {
    AixmFeatureService,
    AixmFeaturePopupService,
    AixmFeatureComponent,
    AixmFeatureDetailComponent,
    AixmFeatureDialogComponent,
    AixmFeaturePopupComponent,
    AixmFeatureDeletePopupComponent,
    AixmFeatureDeleteDialogComponent,
    aixmFeatureRoute,
    aixmFeaturePopupRoute,
} from './';

const ENTITY_STATES = [
    ...aixmFeatureRoute,
    ...aixmFeaturePopupRoute,
];

@NgModule({
    imports: [
        WadeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AixmFeatureComponent,
        AixmFeatureDetailComponent,
        AixmFeatureDialogComponent,
        AixmFeatureDeleteDialogComponent,
        AixmFeaturePopupComponent,
        AixmFeatureDeletePopupComponent,
    ],
    entryComponents: [
        AixmFeatureComponent,
        AixmFeatureDialogComponent,
        AixmFeaturePopupComponent,
        AixmFeatureDeleteDialogComponent,
        AixmFeatureDeletePopupComponent,
    ],
    providers: [
        AixmFeatureService,
        AixmFeaturePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WadeAixmFeatureModule {}
