import React, { useState, useEffect } from 'react';
import { Link, useLocation } from "react-router-dom";
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';

import {
  DataTable,
  DataTableTitle,
  EditButton,
  DeleteButton,
  ActionCell
} from "./RestaurantStyles";

const linkStyle = {
  "textDecoration": "none",
  "color": "green",
  "fontSize": "16px",
  "fontWeight": "400",
  "padding": "5px",
  "borderRadius": "5px",
  "cursor": "pointer"
}


// LINK TAG - CAN PASS STATE DOWN / THROUGH AS A PROP
// NAVLINK - CAN ADD STATE THAT IS PASSED DOWN, OR PASS ITS STATE / VALUE AS A CHILD 
// <NAVLINK > {CHILD VALUE OR ELEMENT TO PASS DOWN} </NAVLINK>

const initialState = {
  id: "",
  restaurantName: "",
  address1: "",
  city: "",
  state: "",
  zip: "",
  owner: "",
  ownerId: ""
};

const RestaurantHome = () => {
  const [restaurants, setRestaurants] = useState([initialState])
  let [user] = useState(JSON.parse(localStorage.getItem("user")));

  const location = useLocation();

  useEffect(() => {

    async function fetchData() {
      try {
        const res = await axios.get(`/restaurant/owner/${user.username}`);
        setRestaurants(res.data);
      } catch (err) {

      }

    }
    fetchData();

  }, [initialState]);


  const handleDelete = async (restaurantId) => {
    try {
      await axios.delete(`/restaurant/${restaurantId}/remove`);
      setRestaurants(restaurants.filter((restaurant) => restaurant.id !== restaurantId));
    } catch (err) {
      console.log(err.response.data)
    }
  }


  const columns = [
    { field: 'id', headerName: 'ID', align: 'left', flex: 1 },
    { field: 'restaurantName', headerName: 'Name', align: 'left', flex: 1 },
    { field: 'address1', headerName: 'Address', align: 'left', flex: 1 },
    { field: 'city', headerName: 'City ', align: 'left', flex: 1 },
    { field: 'state', headerName: 'State', align: 'left', flex: 1 },
    { field: 'zip', headerName: 'Zip', align: 'left', flex: 1 },
    {
      field: 'actions',
      headerName: 'Actions',
      flex: 1,
      align: 'center',
      renderCell: (params) => {
        return params.id === '' ? null :
          <ActionCell>
            <Link style={linkStyle} to={location.pathname + "/" + params.id + "/edit"} >
              <EditButton>Edit</EditButton>
            </Link>
            <Link style={linkStyle} to={location.pathname}>
              <DeleteButton onClick={() => handleDelete(params.id)} >Delete</DeleteButton>
            </Link>
          </ActionCell>

      }
    }
  ];


  return (
    <DataTable>
      <DataTableTitle >
        Restaurants
      </DataTableTitle>

      <div style={{ height: 400, width: '100%' }}>
        <DataGrid
          // checkboxSelection
          rows={restaurants}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[5]}
        />
      </div>
    </DataTable>

  );
}

export default RestaurantHome;


// interface GridColDef {
//   field: string;
//   headerName?: string;
//   description?: string;
//   width?: number;
//   flex?: number;
//   minWidth?: number;
//   hide?: boolean;
//   sortable?: boolean;
//   resizable?: boolean;
//   editable?: boolean;
//   sortComparator?: GridComparatorFn;
//   type?: GridColType;
//   valueOptions?: Array<string | number | {
//       value: any;
//       label: string;
//   }>;
//   align?: GridAlignment;
//   valueGetter?: (params: GridValueGetterParams) => GridCellValue;
//   valueFormatter?: (params: GridValueFormatterParams) => GridCellValue;
//   valueParser?: (value: GridCellValue, params?: GridCellParams) => GridCellValue;
//   cellClassName?: GridCellClassNamePropType;
//   renderCell?: (params: GridRenderCellParams) => React$1.ReactNode;
//   renderEditCell?: (params: GridRenderEditCellParams) => React$1.ReactNode;
//   headerClassName?: GridColumnHeaderClassNamePropType;
//   renderHeader?: (params: GridColumnHeaderParams) => React$1.ReactNode;
//   headerAlign?: GridAlignment;
//   hideSortIcons?: boolean;
//   disableColumnMenu?: boolean;
//   filterable?: boolean;
//   filterOperators?: GridFilterOperator[];
//   disableReorder?: boolean;
//   disableExport?: boolean;
// }