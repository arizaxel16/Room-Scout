import React, { useState } from 'react';
import axios from 'axios';
import './Add.scss';
import { GridColDef } from "@mui/x-data-grid";

type Props = {
  slug: string;
  columns: GridColDef[];
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

const Add = (props: Props) => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    name: '',
    surname: '',
    password: '',
    role: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/users', formData);
      console.log('User created successfully:', response.data);
      alert('User created successfully!');
      
      props.setOpen(false);
    } catch (err) {
      console.error('Error creating user:', err);
      alert('Error creating user: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
    }
  };

  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => props.setOpen(false)}>X</span>
        <h1>Add new {props.slug}</h1>
        <form onSubmit={handleSubmit}>
          {props.columns
            .filter(item => item.field !== "id" && item.field !== "action")
            .map(column => (
              <div className="item" key={column.field}>
                <label>{column.headerName}</label>
                <input
                  type="text"
                  name={column.field}
                  value={formData[column.field as keyof typeof formData]}
                  onChange={handleChange}
                  placeholder={column.field}
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

