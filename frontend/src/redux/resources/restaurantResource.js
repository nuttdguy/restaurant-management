import {
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
} from "../slices/restaurantSlice";

import { authorizedRequest } from "../../api/apiResource";

const registerRestaurant = async (dispatch, restaurant) => {
  dispatch(registerStart());
  try {
    console.log("Registering restaurant ... ", restaurant);
    const res = await authorizedRequest.postForm(
      "/restaurant/create",
      restaurant
    );
    dispatch(registerSuccess(res.data));
  } catch (err) {
    dispatch(registerFailure(err.response?.data?.message));
  }
};

const getRestaurants = async (dispatch) => {
  dispatch(getRestaurantStart());
  try {
    const res = await authorizedRequest.get(`/restaurant`);
    // console.log("Done fetching list of restaurants ", res.data);
    dispatch(getRestaurantSuccess(res.data));
  } catch (err) {
    dispatch(getRestaurantFailure(err.response?.data?.message));
  }
};

const editRestaurant = async (dispatch, dish) => {
  dispatch(editStart());
  try {
    const res = await authorizedRequest.putForm("/restaurant/edit", dish);
    // console.log("Done editing restaurant  ...");
    dispatch(editSuccess(res.data));
  } catch (err) {
    // console.log("Something when wrong editing restaurant  ...");
    dispatch(editFailure(err.response.data?.message));
  }
};

const removeRestaurant = async (dispatch, restaurantId) => {
  dispatch(removeRestaurantStart());
  try {
    console.log("Deleting the restaurant ... ", restaurantId);
    await authorizedRequest.delete(`/restaurant/${restaurantId}`);
    // console.log("Done deleting the restaurant ...");
    dispatch(removeRestaurantSuccess(restaurantId));
  } catch (err) {
    // console.log("Something when wrong deleting the restaurant ...");
    dispatch(removeRestaurantFailure(err.response.data?.message));
  }
};

export { registerRestaurant, getRestaurants, editRestaurant, removeRestaurant };
