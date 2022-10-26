import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

import {
  LineStyle,
  LocalDining,
  ModeEdit,
  MenuBook,
  Logout
} from "@mui/icons-material";

// styled components being used for this component
import {
  Wrapper,
  Menu,
  MenuList,
  MenuItemEvent,
  MenuIcon,
  MenuTitle
} from "./SidebarStyles";

// inline style for react-router-dom Link component
const liStyle = {
  "textDecoration": "none"
}

const iconSize = {
  "width": "18px",
  "height": "18px",
  "padding": "0px 8px"
}


const links = {
  home: "/admin/restaurant",
  create: "/admin/restaurant/create",
  edit: "/admin/restaurant/edit",
  item: "/admin/restaurant/items",
  addItem: "/admin/restaurant/items/create",
  editItem: "/admin/restaurant/items/edit"
}

const dataMemo = {
  uuid: "",
  username: "",
  restaurantName: "",
  restaurantId: "",
}

const Sidebar = () => {
  // const [links] = useState(linksMemo);
  const { logout } = useContext(AuthContext); // extract the login function from the AuthContext
  const navigate = useNavigate(); // returns an iterative method for changing location

  // method to handle logout - clears local storage and changes location to login
  const handleLogout = async () => {
    try {
      await logout();
      navigate("/login")
    } catch (err) {
      console.log(err.response.data)
    }
  }


  // html to transpile and render
  return (
    <Wrapper>
      <Menu>
        <MenuTitle>Dashboard</MenuTitle>
        <MenuList>
          <Link style={liStyle} to={links.home}>
            <MenuItemEvent>
              <MenuIcon >
                <LineStyle style={iconSize} /> Home
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={links.create}>
            <MenuItemEvent>
              <MenuIcon >
                <ModeEdit style={iconSize} /> Add Restaurant
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          {/* <Link style={liStyle} to={links.edit}>
            <MenuItemEvent>
              <MenuIcon  >
                <ModeEdit style={iconSize} /> Edit Restaurant
              </MenuIcon>
            </MenuItemEvent>
          </Link> */}
          <Link style={liStyle} to={links.item}>
            <MenuItemEvent>
              <MenuIcon >
                <MenuBook style={iconSize} /> View Items
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          <Link style={liStyle} to={links.addItem}>
            <MenuItemEvent>
              <MenuIcon >
                <LocalDining style={iconSize} /> Add Item
              </MenuIcon>
            </MenuItemEvent>
          </Link>
          {/* <Link style={liStyle} to={links.editItem}>
            <MenuItemEvent>
              <MenuIcon >
                <LocalDining style={iconSize} /> Edit Item
              </MenuIcon>
            </MenuItemEvent>
          </Link> */}
          <Link onClick={handleLogout} to={"/logout"} style={liStyle}>
            <MenuItemEvent>
              <MenuIcon >
                <Logout style={iconSize} /> Logout
              </MenuIcon>
            </MenuItemEvent>
          </Link>
        </MenuList>

      </Menu>
    </Wrapper>
  )
}

export default Sidebar;