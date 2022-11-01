import styled from "styled-components";

// DB == Dashboard
// wrap the entire dashboard as a flex container
const DBMainSectionWrap = styled.section`
  display: flex;
  flex-direction: column;
`;

//  navbar inherits parents cloumn flow; child elements use this flex definition
const DBNavWrap = styled.section`
  display: inline-flex;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 999;
`;

// wrap the component sections of the dashbaord, i.e. [sidebar, content]
const DBSectionWrap = styled.section`
  display: flex;
  flex-direction: row;
  /* padding: 0 12px 0 20px; */
  height: calc(100vh - 100px);

  position: sticky;
  top: 100px;
`;

// set the width of wrap to 1; set display to flex so that child elements will follow this wraps flow
const DBSidebarWrap = styled.section`
  display: flex;
  flex-direction: column;
  flex: 1;

  padding: 0 10px;
`;

// set wdith of wrap to 4; set display to flex so that child elements will follow this wraps flow
const DBMainContentWrap = styled.section`
  display: flex;
  flex-direction: column;
  flex: 4;
`;

// wrap content info in order to space elements equally amongst column
const DBContentInfoWrap = styled.div`
  display: flex;
  justify-content: space-evenly;
`;

// wrap the header; set to flex to align items within the row
const DBSectionHeader = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  border-bottom: 1px solid gray;

  font-size: 1.3rem;
  color: darkblue;
  text-transform: uppercase;
  letter-spacing: 1.2pt;
  border-bottom: 1px solid gray;
`;

export {
  DBMainSectionWrap,
  DBNavWrap,
  DBSectionWrap,
  DBSectionHeader,
  DBMainContentWrap,
  DBContentInfoWrap,
  DBSidebarWrap,
};
