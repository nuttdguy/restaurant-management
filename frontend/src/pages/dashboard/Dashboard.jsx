import React from "react";
import { Outlet } from "react-router-dom";
import styled from 'styled-components';
import Sidebar from "../../components/sidebar/Sidebar";
import Topbar from "../../components/topbar/Topbar";


const Container = styled.div`
  display: flex;
  margin-top: 10px;
`
const SidebarContainer = styled.article`
  flex: 1;
  height: calc(100vh - 70px);
  background-color: rgb(251, 251, 255);
  position: sticky;
  top: 70px;
`;

// [max] [min] [ideal size];
const ContentContainer = styled.article`
  flex: 4;
  /* background-color: rgb(223, 221, 255); */
  top: 70px;
`

const Dashboard = () => {

  return (
    <>
      <Topbar />
      <Container>
        <SidebarContainer>
          <Sidebar />
        </SidebarContainer>
        <ContentContainer>
          <Outlet />
        </ContentContainer>
      </Container>
    </>
  )
}

export default Dashboard;