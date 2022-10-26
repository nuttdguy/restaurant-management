import React from 'react';
import {AppRouter} from "./AppRouter";

import {
  RouterProvider,
} from "react-router-dom";


const App = () => {

  return (
    <RouterProvider router={AppRouter}> 
    
    </RouterProvider>
  )
}

export default App;