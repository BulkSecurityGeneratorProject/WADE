/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WadeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AixmFeatureDetailComponent } from '../../../../../../main/webapp/app/entities/aixm-feature/aixm-feature-detail.component';
import { AixmFeatureService } from '../../../../../../main/webapp/app/entities/aixm-feature/aixm-feature.service';
import { AixmFeature } from '../../../../../../main/webapp/app/entities/aixm-feature/aixm-feature.model';

describe('Component Tests', () => {

    describe('AixmFeature Management Detail Component', () => {
        let comp: AixmFeatureDetailComponent;
        let fixture: ComponentFixture<AixmFeatureDetailComponent>;
        let service: AixmFeatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WadeTestModule],
                declarations: [AixmFeatureDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AixmFeatureService,
                    JhiEventManager
                ]
            }).overrideTemplate(AixmFeatureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AixmFeatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AixmFeatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AixmFeature(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aixmFeature).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
