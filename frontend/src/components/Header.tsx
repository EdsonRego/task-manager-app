import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";

const Header: React.FC = () => {
  return (
    <header className="header">
      <div className="container">
        <h1 className="logo">Task Manager</h1>
        <nav>
          <Link to="/dashboard">Dashboard</Link>
          <Link to="/tasks/new">Nova Tarefa</Link>
          <Link to="/login">Sair</Link>
        </nav>
      </div>
    </header>
  );
};

export default Header;
