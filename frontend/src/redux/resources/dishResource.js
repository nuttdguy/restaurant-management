import {
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
  removeDishFailure,
  removeDishSuccess,
} from "../slices/dishSlice";

import { authorizedRequest } from "../../api/apiResource";

const fetchDishes = async (dispatch, username) => {
  dispatch(getDishesStart());
  try {
    const res = await authorizedRequest.get(
      `/restaurant/owner/${username}/dishes`
    );
    // console.log("Done fetching list of dishes ", res.data);
    dispatch(getDishesSuccess(res.data));
  } catch (err) {
    dispatch(getDishesFailure(err.response?.data?.message));
  }
};

const newDish = async (dispatch, dish) => {
  dispatch(newDishStart());
  try {
    // console.log("Creating new dish ...");
    const res = await authorizedRequest.postForm(
      "/restaurant/dish/create",
      dish
    );
    // console.log("Done creating new dish ...");
    dispatch(newDishSuccess(res.data));
  } catch (err) {
    console.log("Something went wrong ...", err.response?.data?.message);
    dispatch(newDishFailure(err.response?.data?.message));
  }
};

const editDish = async (dispatch, dish) => {
  dispatch(editDishStart());
  try {
    // console.log("Sending edited dish ...");
    const res = await authorizedRequest.postForm("/restaurant/dish/edit", dish);
    // console.log("Done editing the dish ...");
    dispatch(editDishSuccess(res.data));
  } catch (err) {
    console.log("Something went wrong ...", err.response?.data?.message);
    dispatch(editDishFailure(err.response?.data?.message));
  }
};

const removeDish = async (dispatch, id) => {
  dispatch(removeDishStart());
  try {
    // console.log("Deleting the dish ...");
    const res = await authorizedRequest.delete(`/restaurant/dish/${id}`);

    // console.log("Done deleting the dish ...", id);
    dispatch(removeDishSuccess(res.data));
  } catch (err) {
    console.log("Something went wrong ...");
    dispatch(removeDishFailure(err.response?.data?.message));
  }
};

export { fetchDishes, newDish, editDish, removeDish };
