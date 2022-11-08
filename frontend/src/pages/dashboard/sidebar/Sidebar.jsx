import React from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../../api/authResource";
import {
  Storefront,
  LocalDining,
  Logout,
  ShowChart,
  DashboardCustomizeOutlined,
} from "@mui/icons-material";

import { DBSectionHeader } from "../views/styles/layoutStyles";
import { MenuList, MenuItem, MenuLabel } from "./SidebarStyles";

// inline style for react-router-dom Link component
const linkStyle = {
  padding: "0",
  magrin: "0",
  textDecoration: "none",
  color: "#000",
  fontWeight: "500",
  fontSize: "1rem",
};

const navPath = {
  overview: "/restaurant/overview",
  all: "/restaurant/all",
  dishes: "/restaurant/dishes",
  logout: "/login",
};

export const Sidebar = () => {
  const { currentUser } = useSelector((state) => state.userAuth); // extract the logout action from reducer store
  const dispatch = useDispatch(); // get the dispatch object

  const handleLogout = () => {
    logout(dispatch, currentUser); // clears the user from redux store
    localStorage.clear();
  };

  return (
    <>
      <DBSectionHeader>
        <Link style={linkStyle} to="/restaurant/new">
          <DashboardCustomizeOutlined />
        </Link>
        <h3>dashboard</h3>
      </DBSectionHeader>

      <MenuList>
        <MenuItem>
          <Link style={linkStyle} to={navPath.overview}>
            <MenuLabel>
              {" "}
              <ShowChart /> Overview
            </MenuLabel>
          </Link>
        </MenuItem>

        <MenuItem>
          <Link style={linkStyle} to={navPath.all}>
            <MenuLabel>
              <Storefront />
              Restaurants
            </MenuLabel>
          </Link>
        </MenuItem>
        <MenuItem>
          <Link style={linkStyle} to={navPath.dishes}>
            <MenuLabel>
              <LocalDining />
              Items
            </MenuLabel>
          </Link>
        </MenuItem>
        <MenuItem>
          <Link onClick={handleLogout} to={navPath.logout} style={linkStyle}>
            <MenuLabel>
              <Logout />
              Logout
            </MenuLabel>
          </Link>
        </MenuItem>
      </MenuList>
    </>
  );
};
