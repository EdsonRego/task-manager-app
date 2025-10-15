import React from "react";
import "./Login.css";

const Login: React.FC = () => {
  return (
    <div className="login-page">
      <h2>Bem-vindo</h2>
      <p>Faça login para acessar suas tarefas</p>
      <form className="login-form">
        <input type="email" placeholder="E-mail" required />
        <input type="password" placeholder="Senha" required />
        <button type="submit">Entrar</button>
      </form>
    </div>
  );
};

export default Login;
