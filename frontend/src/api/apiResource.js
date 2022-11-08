import axios from "axios";

let user = JSON.parse(localStorage.getItem("user"));

let CURRENT_USER = user?.username;
let TOKEN = user?.accessToken;

export const publicRequest = axios.create();

export const authorizedRequest = axios.create({
  headers: {
    Authorization: `Bearer ${TOKEN}`,
    username: CURRENT_USER,
  },
});
