import { createSlice } from "@reduxjs/toolkit";

const dishSlice = createSlice({
  name: "dish",
  initialState: {
    dishes: [],
    isFetching: false,
    isSuccess: false,
    isError: false,
    errorMessage: null,
  },
  reducers: {
    getDishesStart: (state) => {
      state.dishes = [];
      state.isFetching = true;
      state.isSuccess = false;
      state.isError = false;
      state.errorMessage = null;
    },
    getDishesSuccess: (state, action) => {
      state.dishes = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isError = false;
      state.errorMessage = null;
    },
    getDishesFailure: (state, action) => {
      state.dishes = [];
      state.isFetching = false;
      state.isSuccess = false;
      state.isError = true;
      state.errorMessage = action.payload;
    },
    newDishStart: (state) => {
      state.isFetching = true;
      state.isSuccess = false;
      state.isError = false;
      state.errorMessage = null;
    },
    newDishSuccess: (state, action) => {
      state.dishes = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isError = false;
      state.errorMessage = null;
    },
    newDishFailure: (state, action) => {
      state.isFetching = false;
      state.isSuccess = false;
      state.isError = true;
      state.errorMessage = action.payload;
    },
    editDishStart: (state) => {
      state.isFetching = true;
      state.isSuccess = false;
      state.isError = false;
      state.errorMessage = null;
    },
    editDishSuccess: (state, action) => {
      state.restaurants[
        state.restaurants.findIndex((rest) => rest.id === action.payload.id)
      ] = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isError = false;
      state.errorMessage = null;
    },
    editDishFailure: (state, action) => {
      state.isFetching = false;
      state.isSuccess = false;
      state.isError = true;
      state.errorMessage = action.payload;
    },
    removeDishStart: (state) => {
      state.isFetching = true;
      state.isSuccess = false;
      state.isError = false;
      state.errorMessage = null;
    },
    removeDishSuccess: (state, action) => {
      state.dishes = action.payload;
      state.isFetching = false;
      state.isSuccess = true;
      state.isError = false;
      state.errorMessage = null;
    },
    removeDishFailure: (state, action) => {
      state.isFetching = false;
      state.isSuccess = false;
      state.isError = true;
      state.errorMessage = action.payload;
    },
  },
});

export const {
  getDishesStart,
  getDishesSuccess,
  getDishesFailure,
  newDishStart,
  newDishSuccess,
  newDishFailure,
  editDishStart,
  editDishSuccess,
  editDishFailure,
  removeDishStart,
  removeDishSuccess,
  removeDishFailure,
} = dishSlice.actions;
export default dishSlice.reducer;
