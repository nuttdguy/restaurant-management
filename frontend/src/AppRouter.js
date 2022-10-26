import React from 'react';

import {
  createBrowserRouter
} from "react-router-dom";

import { 
  Home,
  Register, 
  RegisterAgreement,
  Login, 
  Dashboard,
  RestaurantHome, 
  AddRestaurant,
  EditRestaurant,
  ItemsList,
  AddItem,
  EditItem,
} from "./pages/index.js";


export const AppRouter = createBrowserRouter(
  [{
    path: "/",
    element: <Home />,
    children: 
    [
      {
        index: true,
        path: "register",
        element: <Register />,
      },
      {
        path: "register/verify",
        element: <h1> Link to resend send token - B.E. URL /register/verify/resend </h1>
      },
      {
        path: "register/agreement",
        element: <RegisterAgreement />
      },
      {
        path: "login",
        element: <Login />
      },
      {
        path: "login/password/forgot",
        element: <h1> Request password reseet link - B.E. /login/password/forgot </h1>
      },
      {
        path: "login/password/reset",
        element: <h1> Reset password with link - B.E. /login/password/reset </h1>
      },
      {
        path: "admin",
        element:<Dashboard />,
        children: [{
          index: true,
          path: "/admin/restaurant",
          element: <RestaurantHome />,
        },
        {
          path: "/admin/restaurant/create",
          element: <AddRestaurant />
        }, 
        {
          path: "/admin/restaurant/edit/",
          element: <EditRestaurant />
        }, 
        {
          path: "/admin/restaurant/items",
          element: <ItemsList />
        },
        {
          path: "/admin/restaurant/items/create",
          element: <AddItem />
        },
        {
          path: "/admin/restaurant/items/edit",
          element: <EditItem />
        },
      ]
      },
      {
        path: "*",
        element: <Login />
      }
    ]
    },
]);


      // {
      //   path: "/business_info",
      //   children: [
      //     {
      //       path: "/business_info",
      //       element: <h1> public view of all business listings</h1>
      //     },
      //     {
      //       path: "/business_info/detail/:id",
      //       element: <h1> public view to view single business listing</h1>
      //     },
      //     {
      //       path: "/business_info/search",
      //       element: <h1> public route to search for a listing by query</h1>
      //     },
      //     {
      //       path: "/business_info/:businessId/items",
      //       element: <h1> public route to view all items offered by a this business</h1>
      //     },
      //     {
      //       path: "/business_info/:businessId/item/:id",
      //       element: <h1> public route to view a single item offered by this business</h1>
      //     },
      //   ]
      // },
      // {