import React from 'react'
import styled from "styled-components";


const Title = styled.h1`
  height: 60px;
  font-size: 36px;
  font-weight: 300;
  padding: 10px 12px;
  display: flex;
  align-items: center;
  justify-content: center;
`


const Topbar = ({ user }) => {

  return (
    <>
      <Title>Restaurant Management {user ? ":: " + user.username : ""} </Title>
    </>
  )
}


export default Topbar;