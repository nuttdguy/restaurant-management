import { configureStore } from "@reduxjs/toolkit";

import userAuthReducer from "./slices/userAuthSlice";
import restaurantReducer from "./slices/restaurantSlice";
import dishReducer from "./slices/dishSlice";

export const store = configureStore({
  reducer: {
    userAuth: userAuthReducer,
    restaurant: restaurantReducer,
    dish: dishReducer,
  },
});
