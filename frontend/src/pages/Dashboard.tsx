import React, { useEffect, useState } from "react";
import api from "../api/api";
import Header from "../components/Header";
import TaskItem from "../components/TaskItem";
import "./Dashboard.css";

interface Task {
  id: number;
  title: string;
  description: string;
  completed: boolean;
}

const Dashboard: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    api.get("/tasks").then((response) => setTasks(response.data));
  }, []);

  return (
    <>
      <Header />
      <div className="page-container">
        <h2>Minhas Tarefas</h2>
        {tasks.length === 0 ? (
          <p>Você ainda não tem tarefas cadastradas.</p>
        ) : (
          tasks.map((task) => (
            <TaskItem
              key={task.id}
              id={task.id}
              title={task.title}
              description={task.description}
              completed={task.completed}
            />
          ))
        )}
      </div>
    </>
  );
};

export default Dashboard;
