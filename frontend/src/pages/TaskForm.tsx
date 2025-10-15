import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/api";
import Header from "../components/Header";

const TaskForm: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const taskData = { title, description, completed: false };

    if (id) {
      await api.put(`/tasks/${id}`, taskData);
    } else {
      await api.post("/tasks", taskData);
    }

    navigate("/dashboard");
  };

  return (
    <>
      <Header />
      <div className="page-container">
        <h2>{id ? "Editar Tarefa" : "Nova Tarefa"}</h2>
        <form className="task-form" onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Título"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
          <textarea
            placeholder="Descrição"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
          <button type="submit">Salvar</button>
        </form>
      </div>
    </>
  );
};

export default TaskForm;
