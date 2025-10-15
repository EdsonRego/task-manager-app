import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import "./styles/main.css";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import TaskForm from "./pages/TaskForm";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/tasks/:id" element={<TaskForm />} />
        <Route path="/tasks/new" element={<TaskForm />} />
      </Routes>
    </Router>
  );
}

export default App;
