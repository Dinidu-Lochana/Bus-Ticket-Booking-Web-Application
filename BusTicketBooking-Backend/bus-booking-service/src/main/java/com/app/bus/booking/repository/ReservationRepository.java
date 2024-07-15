package com.app.bus.booking.repository;

import com.app.bus.booking.domain.user.Ad;
import com.app.bus.booking.domain.user.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCompanyId(Long companyId);

    List<Reservation> findAllByUserId(Long UserId);


    List<Reservation> findAllByBookdate(Date bookDate);
}
