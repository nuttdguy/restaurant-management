import React from "react";
import { Outlet } from "react-router-dom";
import styled from 'styled-components';
import Sidebar from "../../components/sidebar/Sidebar";
import {
  Topbar
} from "../../components/topbar/Topbar";

const Container = styled.div`
  display: flex;
  margin-top: 10px;
`

const Dashboard = () => {
  return (
    <>
      <Topbar />
      <Container>
        <Sidebar />
        <Outlet />
      </Container>
    </>
  )
}

export default Dashboard;