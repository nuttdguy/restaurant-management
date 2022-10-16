import React from 'react';
import { Register, Login, User, Business, Dashboard } from "./pages/index.js";
import {
  createBrowserRouter,
  RouterProvider
} from "react-router-dom";


const router = createBrowserRouter([
  {
    path: "/",
    element: <h1>HOME</h1>
  },
  {
    path: "/register",
    element: <Register />,
    children: [
      {
        path: "/register/verify",
        element: <h1> Link to resend send token - BE URL /register/verify/resend </h1>
      },
      {
        path: "/register/agreeemnt",
        element: <h1> Path to view terms and agreement doc</h1>
      }
    ]
  },
  {
    path: "/login",
    element: <Login />,
    children: [
      {
        path: "/login/password/forgot",
        element: <h1> Request password reseet link - B.E. /login/password/forgot </h1>
      },
      {
        path: "/login/password/reset",
        element: <h1> Reset password with link - B.E. /login/password/reset </h1>
      }
    ]
  }, 
  {
    path: "/business_info",
    element: <h1>Business Info</h1>,
    children: [
      {
        path: "/business_info",
        element: <h1> public view of all business listings</h1>
      },
      {
        path: "/business_info/detail/:id",
        element: <h1> public view to view details of a listing</h1>
      },
      {
        path: "/business_info/search=?",
        element: <h1> public route to search for a listing by query</h1>
      },
      {
        path: "/business_info/:id/items",
        element: <h1> public route to view all items of a business listing</h1>
      },
      {
        path: "/business_info/:id/item/:id",
        element: <h1> public route to view a single detail of a business listing</h1>
      },
    ]
  },
  {
    path: "/admin/",
    element: <Dashboard />,
    children: [
    {
      path: "/admin/business",
      element: <Business />,
      children: [{
        path: "/admin/business/:businessName",
        element: <h1>Business listing dashboard of all business listings of owner - B.E. </h1>,
        children: [
          {
            path: "/admin/business/:businessName/listing/create",
            element: <h1>Path to create a business listing - B.E. /business/:name/listing/:id </h1>
          },
          {
            path: "/admin/business/:businessName/listing/:id/edit",
            element: <h1>Path to edit a business listing - B.E. /business/:name/listing/:id</h1>
          },
          {
            path: "/admin/business/:businessName/listing/:id/items",
            element: <h1>Path to view all listing items of the business - B.E. /business/:name/listing/:listId/items</h1>
          },
          {
            path: "/admin/business/:businessName/listing/:id/items/:id",
            element: <h1>Path to to edit a singe listing item </h1>
          }
        ]
      }],
    },
    {
      path: "/admin/user",
      element: <User />
    }
  ]}
]);

const App = () => {

  return (
    <>
        <RouterProvider router={router} />
    </>
  )
}

export default App;