import axios from "axios";
import { Idee } from '@/classes/idea';

export class Helper {

    private static instance: Helper;

    public static getInstance(): Helper {
        if (!Helper.instance) {
            Helper.instance = new Helper();
        }

        return Helper.instance;
    }

    createAxiosInstance() {
        return axios.create({
            baseURL: "http://localhost:9090/benutzer",
            headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "POST, GET, PUT, OPTIONS, DELETE",
            "Access-Control-Allow-Headers":
                "Access-Control-Allow-Methods, Access-Control-Allow-Origin, Origin, Accept, Content-Type",
            "Content-Type": "application/json",
            Accept: "application/json",
            },
        });
    }

    erzeugeDemoDaten(): Idee[] {
        var ideeArray = new Array;
        var today = new Date().toLocaleDateString();

        var idee = new Idee("Gratis Kaffee", "bla bla", "Dieter", today, [{ value: "Geiler Kaffee" }],
            false, "INTERNE_IDEE", "", [{ value: "" }], [{ value: "" }], "ZUKUNFTSFAEHIGKEIT")
        var idee2 = new Idee("Lasst die Hunde los", "bla bla bla bla", "Claudia", today, [{ value: "Süße Wau Wau´s" }],
            false, "PRODUKTIDEE", "KRANKENVERSICHERUNG", [{ value: "DIREKTVERSICHERUNG" }], [{ value: "FAMILIEN" }], "")
        var idee3 = new Idee("Driftrennen", "trallallallaaa", "Klaus", today, [{ value: "Dicker Vorteil" }],
            false, "PRODUKTIDEE", "RECHTSSCHUTZ", [{ value: "STATIONAERER_VERTRIEB" }], [{ value: "SINGLES" }], "")

        ideeArray.push(idee.toString())
        ideeArray.push(idee2.toString())
        ideeArray.push(idee3.toString())

        return ideeArray
    }
}