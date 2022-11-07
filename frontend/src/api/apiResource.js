import axios from "axios";

let user = JSON.parse(localStorage.getItem("user"));
// const user = useSelector((state) => state.userAuth.currentUser);

let CURRENT_USER = user?.username;
let TOKEN = user?.accessToken;
// const REFRESH_TOKEN = user?.refreshToken;
// console.log("apiResource - ", user);

export const publicRequest = axios.create();

export const authorizedRequest = axios.create({
  headers: {
    Authorization: `Bearer ${TOKEN}`,
    username: CURRENT_USER,
  },
});
