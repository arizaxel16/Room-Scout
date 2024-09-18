import React from 'react';
import './Add.scss';

const Add = ({ columns, formData, handleChange, handleSubmit, setOpen, slug }) => {
  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => setOpen(false)}>X</span>
        <h1>Add new {slug}</h1>
        <form onSubmit={handleSubmit}>
          {columns
            .filter(item => item.field !== "id" && item.field !== "action")
            .map(column => (
              <div className="item" key={column.field}>
                <label>{column.headerName}</label>
                <input
                  type="text"
                  name={column.field}
                  value={formData[column.field] || ''} // Si no hay valor, deja vacío
                  onChange={handleChange}
                  placeholder={column.headerName}
                />
              </div>
            ))}
          <button type="submit">Send</button>
        </form>
      </div>
    </div>
  );
};

export default Add;



