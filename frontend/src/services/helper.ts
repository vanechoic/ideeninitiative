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
            headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "POST, GET, PUT, OPTIONS, DELETE",
            "Access-Control-Allow-Headers":
                "Access-Control-Allow-Methods, Access-Control-Allow-Origin, Origin, Accept, Content-Type",
            "Content-Type": "application/json",
            Accept: "application/json",
            Authorization: localStorage.getItem("token")
            },
        });
    }
}