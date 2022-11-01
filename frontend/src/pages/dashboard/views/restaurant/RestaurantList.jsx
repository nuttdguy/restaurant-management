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
    photo: false,
  });

  // [
  //   {
  //     id: "831eaf9d-fb5f-4052-9065-4837559fa3d8",
  //     name: "daytime_place",
  //     alias: null,
  //     url: "daytimeplace@restaurant.com",
  //     phone: "000-000-00000",
  //     category: "Burgers",
  //     description:
  //       "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque egestas semper consequat. Cras congue, sapien a hendrerit tempor, tortor quam volutpat justo, quis maximus neque ex at urna. ",
  //     address1: "Matterson St.",
  //     address2: null,
  //     city: "New Hope",
  //     state: "MN",
  //     zip: "55555",
  //     country: "USA",
  //     img: "https://picsum.photos/200/300",
  //   },
  // ];

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
      valueGetter: (params) => fullAddress(params),
    },
    { field: "phone", headerName: "phone", align: "left", flex: 1 },
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
