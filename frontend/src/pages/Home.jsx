import React from 'react';
import { Outlet } from "react-router-dom";
import Topbar from '../components/topbar/Topbar';

const Home = () => {

  return (
    <>
      <Topbar />
      <Outlet />
    </>
  )
}

export default Home;

