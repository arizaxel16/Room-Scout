import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useHeaderContext } from '../../context/HeaderContext';
import './CategoryResults.css';
import axios from 'axios';
import { format } from 'date-fns'; 

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

    const handleClick = (hotelName, propertyId) => {
        const { dates } = searchData;
        if (!dates || dates.length === 0 || !dates[0].startDate || !dates[0].endDate) {
            alert("Por favor selecciona fechas antes de continuar.");
            return;
        }

   const startDate = format(dates[0].startDate, 'yyyy-MM-dd');
        const endDate = format(dates[0].endDate, 'yyyy-MM-dd');

        navigate(`/rooms/${hotelName}`, {
            state: {
                propertyId: propertyId,
                startDate: startDate,
                endDate: endDate
            }
        });
        // Check if user is authenticated
        const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';

        if (isAuthenticated) {
            navigate(`/rooms/${hotelName}`, {
                state: { dates: searchData.dates }
            });
        } else {
            alert("Por favor, inicie sesión para continuar.");
            navigate('/user_auth'); // Redirect to LoginRegister page
        }
    };

    if (!selectedCategory || properties.length === 0) return null;

    return (
        <div className="categoryResults">
            <h2>{selectedCategory.charAt(0).toUpperCase() + selectedCategory.slice(1)}s</h2>
            <div className="gridContainer">
                {properties.map(item => (
                    <div className="gridItem" key={item.id} onClick={() => handleClick(item.name, item.id)}>
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
