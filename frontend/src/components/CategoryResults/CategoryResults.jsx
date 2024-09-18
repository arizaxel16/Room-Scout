import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useHeaderContext } from '../../context/HeaderContext';
import './CategoryResults.css';
import axios from 'axios';

const CategoryResults = () => {
    const { selectedCategory, searchData } = useHeaderContext();
    const navigate = useNavigate();
    const [properties, setProperties] = React.useState([]);

    React.useEffect(() => {
        const fetchProperties = async () => {
            if (!selectedCategory) return;

            try {
                const response = await axios.get(`http://localhost:8080/properties/type/${selectedCategory}`);
                setProperties(response.data);
            } catch (error) {
                console.error('Error al cargar las propiedades:', error);
            }
        };

        fetchProperties();
    }, [selectedCategory]);

    const handleClick = (hotelName) => {
        const { dates } = searchData;

        
        if (!dates || dates.length === 0 || !dates[0].startDate || !dates[0].endDate) {
            alert("Por favor selecciona fechas antes de continuar.");
            return;
        }

        
        navigate(`/rooms/${hotelName}`, {
            state: { dates: searchData.dates }
        });
    };

    if (!selectedCategory || properties.length === 0) return null;

    return (
        <div className="categoryResults">
            <h2>{selectedCategory.charAt(0).toUpperCase() + selectedCategory.slice(1)}s</h2>
            <div className="gridContainer">
                {properties.map(item => (
                    <div className="gridItem" key={item.id} onClick={() => handleClick(item.name)}>
                        <h3>{item.name}</h3>
                        <p>{item.country}</p>
                        <p>{item.city}</p>
                        <p>{item.address}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CategoryResults;
