import React from 'react';
import { Route, Routes } from 'react-router-dom';
import logo from './logo.svg';
import Home from './pages/Dashbord';
import Login from './pages/Login';

function App() {
  return (
    <div >
      <Routes>
        <Route path="/" element={<Login />}></Route>
        <Route path="/home" element={<Home />}></Route>
      </Routes>
    </div>
  );
}

export default App;
