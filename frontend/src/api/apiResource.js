import axios from "axios";
import { useSelector } from "react-redux";

const user = JSON.parse(localStorage.getItem("user"));
// const user = useSelector((state) => state.userAuth.currentUser);

const CURRENT_USER = user?.username;
const TOKEN = user?.token;
const REFRESH_TOKEN = user?.refreshToken;

export const publicRequest = axios.create();

export const authorizedRequest = axios.create({
  header: {
    token: `Bearer ${TOKEN}`,
  },
});
