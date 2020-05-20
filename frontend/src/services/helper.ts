import axios from "axios";

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
}