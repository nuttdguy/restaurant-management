import {
  loginFailure,
  loginStart,
  loginSuccess,
  logoutStart,
  logoutSuccess,
  logoutFailure,
  registerStart,
  registerSuccess,
  registerFailure,
  clearState,
} from "../redux/slices/userAuthSlice";

import { authorizedRequest, publicRequest } from "./apiResource";

const clearApiError = async (dispatch) => {
  dispatch(clearState());
};

const register = async (dispatch, user) => {
  dispatch(registerStart());
  try {
    const res = await publicRequest.post("/auth/register", user);
    dispatch(registerSuccess(res.data));
  } catch (err) {
    await dispatch(registerFailure(err.response.data?.message));
  }
};

const login = async (dispatch, user) => {
  dispatch(loginStart());
  try {
    const res = await publicRequest.post("/auth/login", null, {
      headers: {
        username: user.username,
        password: user.password,
      },
    });
    const authData = {
      accessToken: res.headers.authorization,
      username: res.headers.username,
    };

    // temp solution - set user to local storage until redux persist
    localStorage.setItem("user", JSON.stringify(authData));
    await dispatch(loginSuccess(authData));
  } catch (err) {
    console.log(err.response.data?.message);
    dispatch(loginFailure(err.response.data?.message));
  }
};

const logout = async (dispatch, user) => {
  dispatch(logoutStart());
  try {
    const res = await authorizedRequest.authorizedRequest("/auth/logout", user);
    dispatch(logoutSuccess(res?.data));
  } catch (err) {
    dispatch(logoutFailure(err.response?.data?.message));
  }
};

export { login, register, logout, clearApiError };
