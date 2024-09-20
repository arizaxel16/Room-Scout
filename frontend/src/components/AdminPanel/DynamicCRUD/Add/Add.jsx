import React from "react";
import "./Add.scss";

const Add = ({
	columns,
	formData,
	handleChange,
	handleSubmit,
	setOpen,
	slug,
	properties,
}) => {
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
								{column.field === "propertyId" && properties.length > 0 ? (
									<select
										name="propertyId"
										value={formData.propertyId || ""}
										onChange={handleChange}
									>
										<option value="">Select a property</option>
										{properties.map((property) => (
											<option key={property.id} value={property.id}>
												{property.name}
											</option>
										))}
									</select>
								) : (
									<input
										type={column.type === "number" ? "number" : "text"}
										name={column.field}
										value={formData[column.field] || ""}
										onChange={handleChange}
										placeholder={column.headerName}
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
