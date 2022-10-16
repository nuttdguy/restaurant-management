import React from 'react'
import { SettingsSuggest, Language } from '@mui/icons-material/';

import {
  Container,
  Wrapper,
  TopLeft,
  TopRight,
  Logo,
  IconContainer,
  IconBadge,
  IconAvatar
} from "./TopbarStyles";


export const Topbar = () => {

  return (
    <Container>
      <Wrapper>

        <TopLeft>
          <Logo>Resturant Management</Logo>
        </TopLeft>

        <TopRight>
          <IconContainer>
            <Language sx={{ fontSize: 42 }} />
            <IconBadge > EN </IconBadge>
          </IconContainer>
          <IconContainer>
            <SettingsSuggest sx={{ fontSize: 42 }} />
          </IconContainer>
          <IconContainer>
            <IconAvatar src="https://i.pravatar.cc/42" />
          </IconContainer>
        </TopRight>

      </Wrapper>
    </Container>
  )
}


// export default Topbar;