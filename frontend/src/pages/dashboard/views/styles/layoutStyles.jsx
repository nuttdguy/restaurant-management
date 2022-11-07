import styled from "styled-components";

// DB == Dashboard
// wrap the entire dashboard as a flex container
const DBMainSectionWrap = styled.section`
  display: flex;
  flex: 100vw;
  flex-direction: column;
  gap: 10px;
`;

//  navbar inherits parents cloumn flow; child elements use this flex definition
const DBNavWrap = styled.nav`
  display: inline-flex;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 999;
  flex: 5;

  padding: 0 12px 0 20px;
  background-color: white;
  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
`;

// wrap the component sections of the dashbaord, i.e. [sidebar, content]
const DBSectionWrap = styled.main`
  display: flex;
  flex-direction: row;

  padding: 0 12px 0 20px;
`;

// set the width of wrap to 1; set display to flex so that child elements will follow this wraps flow
const DBSidebarWrap = styled.section`
  display: flex;
  flex-direction: column;
  height: calc(100vh - 120px);
  flex: 1;

  gap: 10px;
  padding: 0px 10px;

  position: sticky;
  top: 100px;
  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
`;

// set wdith of wrap to 4; set display to flex so that child elements will follow this wraps flow
const DBMainContentWrap = styled.section`
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 120px);
  height: 100%;
  flex: 4;

  gap: 10px;
  padding: 0px 20px;

  border-radius: 10px;
  -webkit-box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 15px -10px rgba(0, 0, 0, 0.75);
`;

// wrap content info in order to space elements equally amongst column
const DBContentInfoWrap = styled.article`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  gap: 0;
  padding: 0;
`;

// wrap the header; set to flex to align items within the row
const DBSectionHeader = styled.div`
  display: flex;
  align-items: center;

  gap: 10px;
  padding: 10px;
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
