import { createSlice } from "@reduxjs/toolkit";

const restaurantSlice = createSlice({
  name: "restaurant",
  initialState: {
    restaurants: [],
    isFetching: false,
    isSuccess: false,
    isApiError: false,
    apiErrorMessage: null,
  },
  reducers: {
    registerStart: (state) => {
      state.restaurants = [];
      state.isFetching = true;
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    registerSuccess: (state, action) => {
      state.restaurants = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    registerFailure: (state, action) => {
      state.restaurants = [];
      state.isFetching = false;
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
    getRestaurantStart: (state) => {
      state.restaurants = [];
      state.isFetching = true;
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    getRestaurantSuccess: (state, action) => {
      state.restaurants = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    getRestaurantFailure: (state, action) => {
      state.restaurants = [];
      state.isFetching = false;
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
    editStart: (state) => {
      state.restaurants = [];
      state.isFetching = true;
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    editSuccess: (state, action) => {
      state.restaurants[
        state.restaurants.findIndex((rest) => rest.id === action.payload.id)
      ] = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    editFailure: (state, action) => {
      state.restaurants = [];
      state.isFetching = false;
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
    removeRestaurantStart: (state) => {
      state.restaurants = [];
      state.isFetching = true;
      state.isSuccess = false;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    removeRestaurantSuccess: (state, action) => {
      state.dishes = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isApiError = false;
      state.apiErrorMessage = null;
    },
    removeRestaurantFailure: (state, action) => {
      state.restaurants = [];
      state.isFetching = false;
      state.isSuccess = false;
      state.isApiError = true;
      state.apiErrorMessage = action.payload;
    },
  },
});

export const {
  registerStart,
  registerSuccess,
  registerFailure,
  getRestaurantStart,
  getRestaurantSuccess,
  getRestaurantFailure,
  editStart,
  editSuccess,
  editFailure,
  removeRestaurantStart,
  removeRestaurantSuccess,
  removeRestaurantFailure,
} = restaurantSlice.actions;
export default restaurantSlice.reducer;
