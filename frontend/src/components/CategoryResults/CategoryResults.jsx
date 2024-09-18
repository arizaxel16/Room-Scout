import React from 'react';
import { useHeaderContext } from '../../context/HeaderContext';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation'; 
import './CategoryResults.css';
import axios from 'axios';

const CategoryResults = () => {
    const { selectedCategory } = useHeaderContext();
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

    if (!selectedCategory || properties.length === 0) return null;

    return (
        <div className="categoryResults">
            <h2>{selectedCategory.charAt(0).toUpperCase() + selectedCategory.slice(1)}s</h2>
            <Swiper
                spaceBetween={20}
                slidesPerView={3}
                loop={true}
                navigation={true}
                pagination={{ clickable: true }}
                breakpoints={{
                    640: {
                        slidesPerView: 1,
                    },
                    768: {
                        slidesPerView: 2,
                    },
                    1024: {
                        slidesPerView: 3,
                    },
                }}
            >
                {properties.map(item => (
                    <SwiperSlide key={item.id}>
                        <div className="resultItem">
                            <h3>{item.name}</h3>
                            <p>{item.country}</p>
                            <p>{item.city}</p>
                            <p>{item.address}</p>
                            
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default CategoryResults;
