import React from "react";

interface TaskProps {
  id: number;
  title: string;
  description: string;
  completed: boolean;
  onEdit?: (id: number) => void;
  onDelete?: (id: number) => void;
}

const TaskItem: React.FC<TaskProps> = ({ id, title, description, completed, onEdit, onDelete }) => {
  return (
    <div className={`task-item ${completed ? "completed" : ""}`}>
      <div className="task-info">
        <h4>{title}</h4>
        <p>{description}</p>
      </div>
      <div className="task-actions">
        <button className="btn btn-edit" onClick={() => onEdit?.(id)}>Editar</button>
        <button className="btn btn-delete" onClick={() => onDelete?.(id)}>Excluir</button>
      </div>
    </div>
  );
};

export default TaskItem;
