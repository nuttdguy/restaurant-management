import React from 'react';

import {
  createBrowserRouter
} from "react-router-dom";

import { 
  Home,
  Register, 
  Login, 
  BusinessHome, 
  BusinessEdit,
  Dashboard,
  BusinessListing,
  RegisterAgreement,
  ItemsList
} from "./pages/index.js";




export const AppRouter = createBrowserRouter(
  [{
    path: "/",
    element: <Home />,
    children: 
    [
      {
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
        element: <Dashboard />,
        children: [{
          path: "/admin/business/:businessName",
          element: <BusinessHome />,
        },
        {
          path: "/admin/business/:businessName/edit",
          element: <BusinessEdit />
        }, 
        {
          path: "/admin/business/:businessName/listing",
          element: <BusinessListing />
        },
        {
          path: "/admin/business/:businessName/listing/create",
          element:  <h1>Path to create a business listing - B.E. /business/:name/listing/:id </h1>
        },
        {
          path: "/admin/business/:businessName/listing/:id/edit",
          element: <h1>Path to edit a business listing - B.E. /business/:name/listing/:id</h1>
        },
        {
          path: "/admin/business/:businessName/listing/:id/items",
          element: <ItemsList />
        },
        {
          path: "/admin/business/:businessName/listing/:id/items/create",
          element: <h1>Path to to create a single item </h1>
        },
        {
          path: "/admin/business/:businessName/listing/:id/items/:id",
          element: <h1>Path to to edit a singe item </h1>
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