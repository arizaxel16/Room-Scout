import React from 'react';
import './DataTable.scss';
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import {Link} from "react-router-dom";
import { FaEdit } from "react-icons/fa";
import { MdDeleteOutline } from "react-icons/md";

const DataTable = ({ columns, rows }) => {
    const handleDelete = (id) => {
        // Lógica para eliminar el usuario con el ID proporcionado
        //axios.delete(`/api/users/${id}`);
        console.log(`Eliminar usuario con ID: ${id}`);
    };
    const columnsWithDates = columns.map(column => {
        if (column.type === 'date') {
            return {
                ...column,
                valueGetter: (params) => new Date(params.value),
            };
        }
        return column;
    });
    // Definir la columna de acciones
    const actionColumn = [
        {
            field: "action",
            headerName: "Action",
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="actionsButtons">
                        <Link to={`/admin/users/${params.row.id}`}><FaEdit className="icon-Edit"/></Link>
                        <div className="delete" onClick={()=>handleDelete(params.row.id)}><MdDeleteOutline className="icon-delete"/></div>
                    </div>
                );
            },
        },
    ];

    // Combinar las columnas originales con la columna de acciones
    const allColumns = [...columns, ...actionColumn];

    return (
        <div className="dataTable">
            <DataGrid
                className="dataGrid"
                rows={rows}
                columns={allColumns}  // Usar todas las columnas
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 5,
                        },
                    },
                }}
                slots={{ toolbar: GridToolbar }}
                slotProps={{
                    toolbar: {
                        showQuickFilter: true,
                        quickFilterProps: { debounceMs: 500 },
                    }
                }}
                pageSizeOptions={[5]}
                checkboxSelection
                disableRowSelectionOnClick
                disableColumnFilter={true}
                disableColumnSelector={true}
                disableDensitySelector={true}
            />
        </div>
    );
};

export default DataTable;
