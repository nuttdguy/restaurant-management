import React from "react";
import axios from "axios";
import { createContext, useEffect, useState } from "react";


// create context to contain auth functions and user identity
export const AuthContext = createContext(); // calling method to create a react context object


// ** requires this context to be a higher order func over main APP context
// create a provider function that will provide the functionality
// to get, delete and store the user identity
export const AuthContextProvider = ({children}) => {
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem("user")) || null
  ); // extract the user from local-storage


  // create an async function to fetch the user from an external resource
  const login = async (inputs) => {
    const res = await axios.post("auth/login", inputs);
    setCurrentUser(res.data);
  }

  // create an async function to logout the user of an external resource
  const logout = async (inputs) => {
    await axios.post("auth/logout");
    setCurrentUser(null);
  }

  // utilize react useEffect to watch for state changes
  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(currentUser));
  }, [currentUser] ); // calls this function whenever currentUser object changes

  // return this provider with the context of currentUser and functions and children objects
  return (
    // the context object to return
    <AuthContext.Provider value={{ currentUser, login, logout }}>
      {children}
    </AuthContext.Provider>
  )

};
