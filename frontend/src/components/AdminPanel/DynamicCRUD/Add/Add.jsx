import React from "react";
import "./Add.scss";

const Add = ({ columns, formData, handleChange, handleSubmit, setOpen, slug, properties }) => {
    return (
        <div className="add">
            <div className="modal">
                <span className="close" onClick={() => setOpen(false)}>
                    X
                </span>
                <h1>Add new {slug}</h1>
                <form onSubmit={handleSubmit}>
                    {columns
                        .filter((item) => item.field !== "id" && item.field !== "action")
                        .map((column) => (
                            <div className="item" key={column.field}>
                                <label>{column.headerName}</label>
                                {column.field === "propertyId" ? (
                                    <select name="propertyId" onChange={handleChange}>
                                        <option value="">Select Property</option>
                                        {properties.map((property) => (
                                            <option key={property.id} value={property.id}>
                                                {property.name}
                                            </option>
                                        ))}
                                    </select>
                                ) : column.field === "role" ? (
                                    <select name="role" onChange={handleChange}>
                                        <option value="Admin">Admin</option>
                                    </select>
                                ) : column.field === "type" ? (
                                    <select name="type" onChange={handleChange}>
                                        <option value="hotel">Hotel</option>
                                        <option value="villa">Villa</option>
                                        <option value="glamping">Glamping</option>
                                        <option value="apartment">Apartment</option>
                                        <option value="motel">Motel</option>
                                        <option value="house">House</option>
                                    </select>
                                ) : (
                                    <input
                                        type={column.type}
                                        placeholder={column.headerName}
                                        name={column.field}
                                        value={formData[column.field] || ""}
                                        onChange={handleChange}
                                    />
                                )}
                            </div>
                        ))}
                    <button type="submit">Send</button>
                </form>
            </div>
        </div>
    );
};

export default Add;
