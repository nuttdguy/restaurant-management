import axios from "axios";

const user = JSON.parse(localStorage.getItem("user"));
// const user = useSelector((state) => state.userAuth.currentUser);

const CURRENT_USER = user?.username;
const TOKEN = user?.accessToken;
// const REFRESH_TOKEN = user?.refreshToken;

export const publicRequest = axios.create();

export const authorizedRequest = axios.create({
  headers: {
    Authorization: `Bearer ${TOKEN}`,
    username: CURRENT_USER,
  },
});
