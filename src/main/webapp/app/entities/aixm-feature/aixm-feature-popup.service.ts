import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AixmFeature } from './aixm-feature.model';
import { AixmFeatureService } from './aixm-feature.service';

@Injectable()
export class AixmFeaturePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private aixmFeatureService: AixmFeatureService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.aixmFeatureService.find(id).subscribe((aixmFeature) => {
                this.aixmFeatureModalRef(component, aixmFeature);
            });
        } else {
            return this.aixmFeatureModalRef(component, new AixmFeature());
        }
    }

    aixmFeatureModalRef(component: Component, aixmFeature: AixmFeature): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aixmFeature = aixmFeature;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
