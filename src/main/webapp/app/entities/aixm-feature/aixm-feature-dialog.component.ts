import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AixmFeature } from './aixm-feature.model';
import { AixmFeaturePopupService } from './aixm-feature-popup.service';
import { AixmFeatureService } from './aixm-feature.service';

@Component({
    selector: 'jhi-aixm-feature-dialog',
    templateUrl: './aixm-feature-dialog.component.html'
})
export class AixmFeatureDialogComponent implements OnInit {

    aixmFeature: AixmFeature;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private aixmFeatureService: AixmFeatureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aixmFeature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aixmFeatureService.update(this.aixmFeature));
        } else {
            this.subscribeToSaveResponse(
                this.aixmFeatureService.create(this.aixmFeature));
        }
    }

    private subscribeToSaveResponse(result: Observable<AixmFeature>) {
        result.subscribe((res: AixmFeature) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AixmFeature) {
        this.eventManager.broadcast({ name: 'aixmFeatureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-aixm-feature-popup',
    template: ''
})
export class AixmFeaturePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aixmFeaturePopupService: AixmFeaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.aixmFeaturePopupService
                    .open(AixmFeatureDialogComponent, params['id']);
            } else {
                this.modalRef = this.aixmFeaturePopupService
                    .open(AixmFeatureDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
