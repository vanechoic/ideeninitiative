import { BehaviorSubject, Observable } from 'rxjs';
var jwt = require("jsonwebtoken");
export class Params {

    private static instance: Params;
    token: object;

    public tokenSubject: BehaviorSubject<object>;
    public readonly tokenObserver: Observable<object>;
    constructor() {
        this.token = jwt.decode(localStorage.getItem("token"));
        this.tokenSubject =  new BehaviorSubject(this.token);
        this.tokenObserver  = this.tokenSubject.asObservable();
    }

    public static getInstance(): Params {
        if (!Params.instance) {
            Params.instance = new Params();
        }

        return Params.instance;
    }
}