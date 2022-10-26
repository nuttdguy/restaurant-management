import React, { useState, useEffect } from 'react';
import { Link, useLocation } from "react-router-dom";
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid';
import styled from 'styled-components';

const Button = styled.button`
  width: 60px;
`

const initialState = {
  id: "",
  name: "",
  price: "",
  description: "",
  ingredients: ""
};


const ItemList = () => {
  const [items, setItems] = useState([]);
  let location = useLocation();

  const handleDelete = async (itemId) => {
    try {
      const restaurantId = "0449571c-53a0-4351-b7dc-e528dd54970e";
      const res = await axios.delete(`/restaurant/${restaurantId}/items/${itemId}/remove`);
      console.log(res.data)
      setItems(items.filter((item) => item.id !== itemId));
    } catch (err) {
      console.log(err.response.data)
    }
  }

  const getItems = async () => {
    try {
      const restaurantId = "0449571c-53a0-4351-b7dc-e528dd54970e";

      const res = await axios.get(`/restaurant/${restaurantId}/items`);
      console.log(res.data)
      setItems(res.data);
    } catch (err) {
      console.log(err.response.data)
    }
  }


  useEffect(() => {
    getItems()

  }, [initialState]);

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'name', headerName: 'Dish Name', width: 100 },
    { field: 'price', headerName: 'Dish Price', width: 100 },
    { field: 'ingredients', headerName: 'Ingredients', width: 140 },
    { field: 'description', headerName: 'Description', width: 200 },
    {
      field: 'edit',
      headerName: 'Edit',
      width: 160,
      renderCell: (params) => {
        return (
          <>
            <Link to={location.pathname + "/" + params.id + "/edit"}>
              <Button>Edit</Button>
            </Link>
            <Button
              onClick={() => handleDelete(params.id)}> Delete</Button>
          </>
        )
      }
    }
  ];


  return (
    <div style={{ height: 400, width: '100%' }}>
      <DataGrid
        checkboxSelection
        rows={items}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
      />
    </div>
  );
}

export default ItemList;