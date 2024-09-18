import React from 'react';
import { useHeaderContext } from '../../context/HeaderContext';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation'; 
import './CategoryResults.css';

const CategoryResults = () => {
    const { selectedCategory, data } = useHeaderContext();

    if (!selectedCategory || !data[selectedCategory]) return null;

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
                {data[selectedCategory]?.map(item => (
                    <SwiperSlide key={item.id}>
                        <div className="resultItem">
                            <img src={item.image} alt={item.name} className="resultImage" />
                            <h3>{item.name}</h3>
                            <p>{item.location}</p>
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default CategoryResults;