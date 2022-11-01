import React from "react";
import { Outlet } from "react-router-dom";
import styled from "styled-components";
import { Navbar } from "../components/navbar/Navbar";
import { Sidebar } from "../components/sidebar/Sidebar";

const FlexContainer = styled.section`
  display: flex;
  flex-direction: row;
`;

export const Dashboard = () => {
  return (
    <>
      <Navbar />
      <FlexContainer>
        <Sidebar />
        <Outlet />
      </FlexContainer>
    </>
  );
};
