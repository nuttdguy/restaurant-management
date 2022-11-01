import React from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../../api/authResource";
import {
  Storefront,
  LocalDining,
  Logout,
  ShowChart,
} from "@mui/icons-material";

import { Wrapper, MenuTitle, MenuList, MenuItem } from "./SidebarStyles";

// inline style for react-router-dom Link component
const linkStyle = {
  textDecoration: "none",
  color: "#000",
  fontSize: "18px",
};

const iconSize = {
  padding: "0px 8px",
  fontSize: "21px",
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
    <Wrapper>
      <MenuTitle>Dashboard</MenuTitle>
      <MenuList>
        <MenuItem>
          <Link style={linkStyle} to={navPath.overview}>
            <ShowChart style={iconSize} /> Overview
          </Link>
        </MenuItem>

        <MenuItem>
          <Link style={linkStyle} to={navPath.all}>
            <Storefront style={iconSize} /> Restaurants
          </Link>
        </MenuItem>
        <MenuItem>
          <Link style={linkStyle} to={navPath.dishes}>
            <LocalDining style={iconSize} /> Items
          </Link>
        </MenuItem>
        <MenuItem>
          <Link onClick={handleLogout} to={navPath.logout} style={linkStyle}>
            <Logout style={iconSize} /> Logout
          </Link>
        </MenuItem>
      </MenuList>
    </Wrapper>
  );
};
