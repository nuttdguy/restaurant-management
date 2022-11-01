import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { DataGrid } from "@mui/x-data-grid";
import { Link, useLocation } from "react-router-dom";
import {
  getRestaurants,
  removeRestaurant,
} from "../../../../redux/resources/restaurantResource";

import { AddBoxOutlined, DeleteOutline, Edit } from "@mui/icons-material";
import { DBSectionWrap, DBSectionHeader } from "../styles/layoutStyles";

import {
  // Wrapper,
  // MenuTitle,
  DataTable,
  ActionCell,
  DeleteButton,
} from "../styles/RestaurantStyle";

const linkStyle = {
  // textDecoration: "none",
  // color: "darkblue",
  // fontSize: "16px",
  // fontWeight: "400",
  // padding: "5px",
  // borderRadius: "5px",
  // cursor: "pointer",
};

export const RestaurantList = () => {
  const dispatch = useDispatch();
  const location = useLocation();
  const restaurants = useSelector((state) => state.restaurant.restaurants);
  const username = useSelector((state) => state.userAuth.currentUser?.username);
  // const username = JSON.parse(localStorage.getItem("user"))?.username;
  const [columnVisibility, setColumnVisibility] = useState({
    alias: false,
    url: false,
    description: false,
    category: false,
    address1: false,
    address2: false,
    city: false,
    state: false,
    zip: false,
    image: false,
  });

  const handleDelete = (restaurantId) => {
    removeRestaurant(dispatch, restaurantId);
  };

  useEffect(() => {
    getRestaurants(dispatch, username);
  }, [dispatch, username]);

  function fullAddress(params) {
    return `${params.row.address1}, ${params.row.city}, ${params.row.state}, ${params.row.zip}`;
  }

  const columns = [
    { field: "id", headerName: "ID", align: "left", flex: 1 },
    {
      field: "name",
      headerName: "Name",
      align: "left",
      flex: 2,
    },
    {
      field: "alias",
      headerName: "Alias",
      align: "left",
      flex: 1,
    },
    {
      field: "url",
      headerName: "url",
      align: "left",
      flex: 1,
    },
    {
      field: "description",
      headerName: "description",
      align: "left",
      flex: 1,
    },
    { field: "category", headerName: "category", align: "left", flex: 1 },
    {
      field: "address",
      headerName: "Address",
      align: "left",
      flex: 2,
      valueGetter: fullAddress,
    },
    { field: "phone", headerName: "phone", align: "left", flex: 1 },
    { field: "address1", headerName: "Address1", align: "left", flex: 1 },
    { field: "address2", headerName: "Address2", align: "left", flex: 1 },
    { field: "city", headerName: "City ", align: "left", flex: 1 },
    { field: "state", headerName: "State", align: "left", flex: 1 },
    { field: "zip", headerName: "Zip", align: "left", flex: 1 },
    { field: "image", headerName: "image", align: "left", flex: 1 },
    {
      field: "actions",
      headerName: "Actions",
      flex: 1,
      align: "center",
      renderCell: (params) => {
        return params.id === "" ? null : (
          <ActionCell>
            <Link
              style={linkStyle}
              to={
                location.pathname.slice(0, location.pathname.lastIndexOf("/")) +
                "/" +
                params.id +
                "/edit"
              }
            >
              <Edit>edit</Edit>
            </Link>
            <DeleteButton onClick={() => handleDelete(params.id)}>
              <DeleteOutline />
            </DeleteButton>
          </ActionCell>
        );
      },
    },
  ];

  return (
    <DBSectionWrap>
      <DBSectionHeader>
        Restaurants
        <Link style={linkStyle} to="/restaurant/new">
          <AddBoxOutlined />
        </Link>
      </DBSectionHeader>
      {/* {JSON.stringify(restaurants)} */}
      <DataTable>
        <div style={{ height: 400, width: "100%" }}>
          <DataGrid
            // checkboxSelection
            columnVisibilityModel={columnVisibility}
            onColumnVisibilityModelChange={(newModel) =>
              setColumnVisibility(newModel)
            }
            // getRowId={(row) => row.uuid}
            rows={restaurants}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
          />
        </div>
      </DataTable>
      {/* </FlexItem> */}
    </DBSectionWrap>
  );
};
