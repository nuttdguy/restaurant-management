import React from 'react';
import { Outlet, useLocation } from "react-router-dom";
import Login from './login/Login';
import Register from './register/Register';

const Home = () => {
  let location = useLocation();

  // let outletContext = useOutletContext(); // context passed into outlet 
  // let [searchParams, setSearchParams] = useSearchParams(); // extract search params from url 

  return (
    <>
      {/* <Topbar user={currentUser} /> */}
      {location.pathname === "/" || location.pathname === "/login" ? <Login /> :
        location.pathname === "/register" ? <Register /> :
          // <RequireAuth children={{ currentUser }}><Outlet /> </RequireAuth>
          // todo fix bug - order of execution is incorrect; user is null 
          <Outlet />
      }
    </>
  )
}

export default Home;

