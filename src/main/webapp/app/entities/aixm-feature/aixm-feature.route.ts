import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AixmFeatureComponent } from './aixm-feature.component';
import { AixmFeatureDetailComponent } from './aixm-feature-detail.component';
import { AixmFeaturePopupComponent } from './aixm-feature-dialog.component';
import { AixmFeatureDeletePopupComponent } from './aixm-feature-delete-dialog.component';

import { Principal } from '../../shared';

export const aixmFeatureRoute: Routes = [
    {
        path: 'aixm-feature',
        component: AixmFeatureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wadeApp.aixmFeature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aixm-feature/:id',
        component: AixmFeatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wadeApp.aixmFeature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aixmFeaturePopupRoute: Routes = [
    {
        path: 'aixm-feature-new',
        component: AixmFeaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wadeApp.aixmFeature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aixm-feature/:id/edit',
        component: AixmFeaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wadeApp.aixmFeature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aixm-feature/:id/delete',
        component: AixmFeatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wadeApp.aixmFeature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
