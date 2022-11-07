import React from "react";
import { useSelector } from "react-redux";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import {
  Landing,
  Login,
  Register,
  RegisterAgreement,
  Dashboard,
  Overview,
  RestaurantList,
  NewRestaurant,
  EditRestaurant,
  DishList,
  NewDish,
  EditDish,
} from "./pages/pagesIndex";

const App = () => {
  const user = useSelector((state) => state.userAuth.currentUser);
  // todo add middleware or other alternauves for protecting routes

  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/agreement" element={<RegisterAgreement />} />

        {user && (
          <Route path="/restaurant" element={<Dashboard />}>
            <Route path="/restaurant/overview" element={<Overview />} />
            <Route path="/restaurant/all" element={<RestaurantList />} />
            <Route path="/restaurant/new" element={<NewRestaurant />} />
            <Route path="/restaurant/:id/edit" element={<EditRestaurant />} />
            <Route path="/restaurant/dishes" element={<DishList />} />
            <Route path="/restaurant/dishes/new" element={<NewDish />} />
            <Route path="/restaurant/dishes/:id/edit" element={<EditDish />} />
          </Route>
        )}
        <Route path="*" element={<Login />} />
      </Routes>
    </Router>
  );
};

export default App;
