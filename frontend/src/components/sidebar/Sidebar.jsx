import React, { useContext } from "react";
import { Link, useNavigate, redirect } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

import {
  LineStyle,
  Inventory,
  LocalDining,
  ModeEdit,
  MenuBook,
  Logout
} from "@mui/icons-material";

import {
  Wrapper,
  Menu,
  MenuTitle,
  MenuList,
  MenuItemEvent,
  MenuIcon
} from "./SidebarStyles";

const liStyle = {
  "textDecoration": "none"
}

const Sidebar = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await logout();
      navigate("/login");
      // navigate({ to: "/login", options: { relative: "/" } });
    } catch (err) {
      console.log(err.response.data)
    }
  }

  // extract location from url
  const home = "/admin/business/janedoe";
  const edit = home + "/edit";
  const listingUrl = home + "/listing";
  const itemsUrl = listingUrl + "/1/items";
  const itemsAddUrl = itemsUrl + "/create";

  return (
    <Wrapper>
      <Menu>
        <MenuTitle>
          Menu
        </MenuTitle>
        <MenuList>
          <Link style={liStyle} to={home}>
            <MenuItemEvent>
              <MenuIcon>
                <LineStyle /> Home
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={edit}>
            <MenuItemEvent>
              <MenuIcon>
                <ModeEdit /> Edit
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={listingUrl}>
            <MenuItemEvent>
              <MenuIcon>
                <Inventory /> Listing
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={itemsUrl}>
            <MenuItemEvent>
              <MenuIcon>
                <MenuBook /> Items
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={itemsAddUrl}>
            <MenuItemEvent>
              <MenuIcon>
                <LocalDining /> Add Item
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link onClick={handleLogout} style={liStyle}>
            <MenuItemEvent>
              <MenuIcon>
                <Logout /> Logout
              </MenuIcon>
            </MenuItemEvent>
          </Link>
        </MenuList>

      </Menu>
    </Wrapper>
  )
}

export default Sidebar;