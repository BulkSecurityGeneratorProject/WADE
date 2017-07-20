import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AixmFeature } from './aixm-feature.model';
import { AixmFeatureService } from './aixm-feature.service';

@Component({
    selector: 'jhi-aixm-feature-detail',
    templateUrl: './aixm-feature-detail.component.html'
})
export class AixmFeatureDetailComponent implements OnInit, OnDestroy {

    aixmFeature: AixmFeature;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private aixmFeatureService: AixmFeatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAixmFeatures();
    }

    load(id) {
        this.aixmFeatureService.find(id).subscribe((aixmFeature) => {
            this.aixmFeature = aixmFeature;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAixmFeatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aixmFeatureListModification',
            (response) => this.load(this.aixmFeature.id)
        );
    }
}
