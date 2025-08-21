import React from 'react';
import Dashboard from "/src/pages/Dashboard.jsx";
import ProjectCreatePage from "/src/pages/ProjectCreatePage.jsx";
import {Route, Routes,} from "react-router-dom";
import App from "/src/App.jsx";

const AppRouter = () => {
  return (
    <Routes>
      <Route element={<App/>}>
        <Route path="/" element={<Dashboard/>}/>
        <Route path="/create-project" element={<ProjectCreatePage/>}/>
      </Route>
    </Routes>
  );
};

export default AppRouter;