import axios from "axios";

const user = JSON.parse(localStorage.getItem("user"));

const CURRENT_USER = user?.username;
const TOKEN = user?.token;
const REFRESH_TOKEN = user?.refreshToken;

export const publicRequest = axios.create();

export const authorizedRequest = axios.create({
  header: {
    token: `Bearer ${TOKEN}`,
  },
});
