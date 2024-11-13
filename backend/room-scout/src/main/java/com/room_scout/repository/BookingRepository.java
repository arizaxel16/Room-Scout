package com.room_scout.repository;

import com.room_scout.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
       @Query("SELECT b FROM Booking b WHERE b.roomType.id = :roomTypeId AND " +
                     "(b.startDate <= :endDate AND b.endDate >= :startDate)")
       List<Booking> findByRoomTypeAndDateOverlap(@Param("roomTypeId") Long roomTypeId,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate);
}
