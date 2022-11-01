import { createSlice } from "@reduxjs/toolkit";

const userAuthSlice = createSlice({
  name: "user",
  initialState: {
    currentUser: null,
    isSuccess: false,
    isApiError: false,
    apiErrorMessage: null,
  },
  reducers: {
    loginStart: (state) => {
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    loginSuccess: (state, action) => {
      state.currentUser = action.payload;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    loginFailure: (state, action) => {
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
    logoutStart: (state) => {
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    logoutSuccess: (state) => {
      state.currentUser = null;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    logoutFailure: (state) => {
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = null;
    },
    registerStart: (state) => {
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    registerSuccess: (state) => {
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    registerFailure: (state, action) => {
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
    clearState: (state) => {
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
  },
});

export const {
  loginStart,
  loginSuccess,
  loginFailure,
  logoutStart,
  logoutSuccess,
  logoutFailure,
  registerStart,
  registerSuccess,
  registerFailure,
  clearState,
} = userAuthSlice.actions;
export default userAuthSlice.reducer;
