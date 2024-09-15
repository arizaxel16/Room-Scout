package com.room_scout.repository;

import com.room_scout.model.BookingAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingAddOnRepository extends JpaRepository<BookingAddOn, Long> {
}
