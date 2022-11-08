import React, { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { DataGrid } from "@mui/x-data-grid";
import { Avatar } from "@mui/material";
import { AddBoxOutlined, DeleteOutline, Edit } from "@mui/icons-material";
import { DataTable, ActionCell, DeleteButton } from "../styles/DishStyle";
import { DBSectionHeader, DBContentInfoWrap } from "../styles/layoutStyles";

import {
  fetchDishes,
  removeDish,
} from "../../../../redux/resources/dishResource";

export function DishList() {
  const dispatch = useDispatch();
  const location = useLocation();
  const dishes = useSelector((state) => state.dish.dishes);

  const locationPath = () => location.pathname;

  const handleDelete = (dishId) => {
    removeDish(dispatch, dishId);
  };

  useEffect(() => {
    fetchDishes(dispatch);
  }, [dispatch]);

  const columns = [
    { field: "id", headerName: "ID", align: "left", flex: 1 },
    {
      field: "photos",
      headerName: "Photos",
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
    {
      field: "name",
      headerName: "Dish name",
      align: "left",
      flex: 1,
    },
    { field: "price", headerName: "Price ", align: "left", flex: 1 },
    { field: "description", headerName: "Desc ", align: "left", flex: 1 },
    { field: "ingredients", headerName: "Ingredients", align: "left", flex: 1 },
    {
      field: "actions",
      headerName: "Actions",
      flex: 1,
      align: "center",
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
        <h3>Dish Items</h3>
        <Link to="/restaurant/dishes/new">
          <AddBoxOutlined />
        </Link>
      </DBSectionHeader>
      {/* {JSON.stringify(dishes)} */}
      <DBContentInfoWrap>
        <DataTable>
          <DataGrid
            checkboxSelection
            disableSelectionOnClick
            rows={dishes}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
          />
        </DataTable>
      </DBContentInfoWrap>
    </>
  );
}
