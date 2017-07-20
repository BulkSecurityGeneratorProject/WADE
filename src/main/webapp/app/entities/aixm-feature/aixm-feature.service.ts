import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AixmFeature } from './aixm-feature.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AixmFeatureService {

    private resourceUrl = 'api/aixm-features';
    private resourceSearchUrl = 'api/_search/aixm-features';

    constructor(private http: Http) { }

    create(aixmFeature: AixmFeature): Observable<AixmFeature> {
        const copy = this.convert(aixmFeature);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(aixmFeature: AixmFeature): Observable<AixmFeature> {
        const copy = this.convert(aixmFeature);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AixmFeature> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(aixmFeature: AixmFeature): AixmFeature {
        const copy: AixmFeature = Object.assign({}, aixmFeature);
        return copy;
    }
}
