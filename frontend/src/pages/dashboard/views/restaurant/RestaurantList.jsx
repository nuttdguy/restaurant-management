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
import { Avatar, Box } from "@mui/material";

export function RestaurantList() {
  const dispatch = useDispatch();
  const location = useLocation();
  const restaurants = useSelector((state) => state.restaurant?.restaurants);
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
    photo: true,
  });

  const handleDelete = (restaurantId) => {
    removeRestaurant(dispatch, restaurantId);
  };

  const locationPath = () =>
    location.pathname.slice(0, location.pathname.lastIndexOf("/"));

  function fullAddress(params) {
    return `${params.address1}, ${params.city}, ${params.state}, ${params.zip}`;
  }

  useEffect(() => {
    getRestaurants(dispatch);
  }, [dispatch]);

  const columns = [
    { field: "id", headerName: "ID", align: "left", flex: 1 },
    {
      field: "photos",
      headerName: "Photo",
      align: "center",
      flex: 1,
      renderCell: (params) => (
        <>
          {params.row.photos?.map((photo, index) => (
            <Avatar
              key={photo.id}
              src={`${photo.photoUrl}`}
              alt={photo.name}
              loading="lazy"
            />
          ))}
        </>
      ),
    },
    { field: "name", headerName: "Name", align: "left", flex: 2 },
    { field: "alias", headerName: "Alias", align: "left" },
    { field: "url", headerName: "url", align: "left" },
    { field: "description", headerName: "description", align: "left" },
    { field: "category", headerName: "category", align: "left" },
    {
      field: "address",
      headerName: "Address",
      align: "left",
      flex: 3,
      valueGetter: (params) => fullAddress(params.row?.address),
    },
    { field: "phone", headerName: "phone", align: "left", flex: 2 },
    { field: "address1", headerName: "Address1", align: "left" },
    { field: "address2", headerName: "Address2", align: "left" },
    { field: "city", headerName: "City ", align: "left" },
    { field: "state", headerName: "State", align: "left" },
    { field: "zip", headerName: "Zip", align: "left" },
    {
      field: "actions",
      headerName: "Actions",
      align: "center",
      flex: 1,
      renderCell: (params) => (
        <ActionCell>
          <Link to={`${locationPath()}/${params.row.id}/edit`}>
            <Edit>edit</Edit>
          </Link>
          <DeleteButton onClick={() => handleDelete(params.row.id)}>
            <DeleteOutline />
          </DeleteButton>
        </ActionCell>
      ),
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
          {/* <Box sx={{ height: 520, width: "100%" }}> */}
          <DataGrid
            checkboxSelection
            // disableSelectionOnClick
            columnVisibilityModel={columnVisibility}
            onColumnVisibilityModelChange={(newModel) =>
              setColumnVisibility(newModel)
            }
            rows={restaurants}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
          />
          {/* </Box> */}
        </DataTable>
      </DBContentInfoWrap>
    </>
  );
}
