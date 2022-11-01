import React, { useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Link, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { AddBoxOutlined, DeleteOutline, Edit } from "@mui/icons-material";
import { Wrapper, MenuTitle, DataTable } from "../styles/DishStyle";

import { ActionCell, DeleteButton } from "../styles/DishStyle";
import {
  fetchDishes,
  removeDish,
} from "../../../../redux/resources/dishResource";

const linkStyle = {
  textDecoration: "none",
  color: "darkblue",
  fontSize: "16px",
  fontWeight: "400",
  padding: "5px",
  borderRadius: "5px",
  cursor: "pointer",
};

export const DishList = () => {
  const dispatch = useDispatch();
  const dishes = useSelector((state) => state.dish.dishes);
  const username = JSON.parse(localStorage.getItem("user"))?.username;

  const location = useLocation();

  const handleDelete = async (dishId) => {
    removeDish(dispatch, dishId);
  };

  useEffect(() => {
    fetchDishes(dispatch, username);
  }, [dispatch, username]);

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
              style={linkStyle}
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
    <Wrapper>
      <MenuTitle>
        Dish Items
        <Link style={linkStyle} to="/restaurant/dishes/new">
          <AddBoxOutlined />
        </Link>
      </MenuTitle>
      {/* {JSON.stringify(dishes)} */}
      <DataTable>
        <div style={{ height: 400, width: "100%" }}>
          {dishes === {} ? null : (
            <DataGrid
              // checkboxSelection
              rows={dishes}
              columns={columns}
              pageSize={5}
              rowsPerPageOptions={[5]}
            />
          )}
        </div>
      </DataTable>
    </Wrapper>
  );
};
