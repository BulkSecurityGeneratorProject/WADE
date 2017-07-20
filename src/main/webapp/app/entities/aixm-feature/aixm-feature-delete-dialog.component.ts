import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AixmFeature } from './aixm-feature.model';
import { AixmFeaturePopupService } from './aixm-feature-popup.service';
import { AixmFeatureService } from './aixm-feature.service';

@Component({
    selector: 'jhi-aixm-feature-delete-dialog',
    templateUrl: './aixm-feature-delete-dialog.component.html'
})
export class AixmFeatureDeleteDialogComponent {

    aixmFeature: AixmFeature;

    constructor(
        private aixmFeatureService: AixmFeatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aixmFeatureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aixmFeatureListModification',
                content: 'Deleted an aixmFeature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aixm-feature-delete-popup',
    template: ''
})
export class AixmFeatureDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aixmFeaturePopupService: AixmFeaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aixmFeaturePopupService
                .open(AixmFeatureDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
