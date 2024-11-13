import React from "react";
import "./DataTable.scss";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { FaEdit } from "react-icons/fa";
import { MdDeleteOutline } from "react-icons/md";

const DataTable = ({ columns, rows, handleUpdate, handleDelete }) => {
    const actionColumn = [
        {
            field: "action",
            headerName: "Action",
            width: 200,
            renderCell: (params) => (
                <div className="actionsButtons">
                    <div onClick={() => handleUpdate(params.row.id, params.row)}>
                        <FaEdit className="icon-Edit" />
                    </div>
                    <div onClick={() => handleDelete(params.row.id)}>
                        <MdDeleteOutline className="icon-delete" />
                    </div>
                </div>
            )
        }
    ];

    return (
        <div className="dataTable">
            <DataGrid
                className="dataGrid"
                rows={rows}
                columns={[...columns, ...actionColumn]}
                pageSizeOptions={[5]}
                slots={{ toolbar: GridToolbar }}
                slotProps={{
                    toolbar: {
                        showQuickFilter: true,
                        quickFilterProps: { debounceMs: 500 }
                    }
                }}
                checkboxSelection
                disableRowSelectionOnClick
            />
        </div>
    );
};

export default DataTable;
