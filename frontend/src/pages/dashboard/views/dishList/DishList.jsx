import React, { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { DataGrid } from "@mui/x-data-grid";
import { AddBoxOutlined, DeleteOutline, Edit } from "@mui/icons-material";
import { DataTable, ActionCell, DeleteButton } from "../styles/DishStyle";
import { DBSectionHeader, DBContentInfoWrap } from "../styles/layoutStyles";

import {
  fetchDishes,
  removeDish,
} from "../../../../redux/resources/dishResource";

export const DishList = () => {
  const dispatch = useDispatch();
  const location = useLocation();
  const dishes = useSelector((state) => state.dish.dishes);
  const user = useSelector((state) => state.userAuth.currentUser);

  const handleDelete = async (dishId) => {
    removeDish(dispatch, dishId);
  };

  useEffect(() => {
    fetchDishes(dispatch, user.username);
  }, [dispatch, user.username]);

  const columns = [
    { field: "id", headerName: "ID", align: "left", flex: 1 },
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
      renderCell: (params) => {
        return params.id === "" ? null : (
          <ActionCell>
            <Link
              to={
                location.pathname.slice(0, location.pathname.lastIndexOf("/")) +
                "/edit/" +
                params.id
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
    <>
      <DBSectionHeader>
        <h3>Dish Items</h3>
        <Link to="/restaurant/dishes/new">
          <AddBoxOutlined />
        </Link>
      </DBSectionHeader>
      {JSON.stringify(dishes)}
      <DBContentInfoWrap>
        <DataTable>
          {dishes === {} ? null : (
            <DataGrid
              // checkboxSelection
              rows={dishes}
              columns={columns}
              pageSize={5}
              rowsPerPageOptions={[5]}
            />
          )}
        </DataTable>
      </DBContentInfoWrap>
    </>
  );
};
