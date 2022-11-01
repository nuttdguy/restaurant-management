import React from "react";
import { Outlet } from "react-router-dom";
import { Navbar } from "../components/navbar/Navbar";
import { Sidebar } from "../../pages/dashboard/sidebar/Sidebar";
import {
  DBMainSectionWrap,
  DBNavWrap,
  DBSectionWrap,
  DBSidebarWrap,
  DBMainContentWrap,
} from "./views/styles/layoutStyles";

export const Dashboard = () => {
  return (
    <DBMainSectionWrap>
      <DBNavWrap>
        <Navbar />
      </DBNavWrap>
      <DBSectionWrap>
        <DBSidebarWrap>
          <Sidebar />
        </DBSidebarWrap>
        <DBMainContentWrap>
          <Outlet />
        </DBMainContentWrap>
      </DBSectionWrap>
    </DBMainSectionWrap>
  );
};
