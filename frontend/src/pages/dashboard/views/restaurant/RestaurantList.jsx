import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { DataGrid } from "@mui/x-data-grid";
import { Link, useLocation } from "react-router-dom";
import {
  getRestaurants,
  removeRestaurant,
} from "../../../../redux/resources/restaurantResource";

import { AddBoxOutlined, DeleteOutline, Edit } from "@mui/icons-material";
import { DBSectionHeader, DBContentInfoWrap } from "../styles/layoutStyles";
import { DataTable, ActionCell, DeleteButton } from "../styles/RestaurantStyle";

export const RestaurantList = () => {
  const dispatch = useDispatch();
  const location = useLocation();
  const restaurants = useSelector((state) => state.restaurant.restaurants);
  const username = useSelector((state) => state.userAuth.currentUser?.username);
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

  const handleDelete = (restaurantId) =>
    removeRestaurant(dispatch, restaurantId);

  const locationPath = () =>
    location.pathname.slice(0, location.pathname.lastIndexOf("/"));

  const fullAddress = (params) => {
    return `${params.row.address1}, ${params.row.city}, ${params.row.state}, ${params.row.zip}`;
  };

  useEffect(() => {
    getRestaurants(dispatch, username);
  }, [dispatch, username]);

  const columns = [
    { field: "id", headerName: "ID", align: "left", flex: 1 },
    { field: "name", headerName: "Name", align: "left", flex: 1 },
    { field: "alias", headerName: "Alias", align: "left" },
    { field: "url", headerName: "url", align: "left" },
    { field: "description", headerName: "description", align: "left" },
    { field: "category", headerName: "category", align: "left" },
    {
      field: "address",
      headerName: "Address",
      align: "left",
      flex: 2,
      valueGetter: (params) => fullAddress(params),
    },
    { field: "phone", headerName: "phone", align: "left", flex: 1 },
    { field: "address1", headerName: "Address1", align: "left" },
    { field: "address2", headerName: "Address2", align: "left" },
    { field: "city", headerName: "City ", align: "left" },
    { field: "state", headerName: "State", align: "left" },
    { field: "zip", headerName: "Zip", align: "left" },
    { field: "image", headerName: "image", align: "left" },
    {
      field: "actions",
      headerName: "Actions",
      align: "center",
      flex: 1,
      renderCell: (params) => {
        return params.id === "" ? null : (
          <ActionCell>
            <Link to={`${locationPath()}/${params.id}/edit`}>
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
    <>
      <DBSectionHeader>
        <h3>Restaurants</h3>
        <Link to="/restaurant/new">
          <AddBoxOutlined />
        </Link>
      </DBSectionHeader>
      {/* {JSON.stringify(restaurants)} */}
      <DBContentInfoWrap>
        <DataTable>
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
        </DataTable>
      </DBContentInfoWrap>
    </>
  );
};
