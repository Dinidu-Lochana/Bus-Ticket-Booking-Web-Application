package com.app.bus.booking.repository;

import com.app.bus.booking.domain.user.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAllByUserId(Long userId);

    @Query("SELECT a.imagePath FROM Ad a WHERE a.id = :adId")
    String findImagePathByAdId(@Param("adId") Long adId);

    @Query("SELECT a FROM Ad a WHERE a.fromTown = :fromTown AND a.toTown = :toTown")
    List<Ad> findAllByFromTownAndToTown(@Param("fromTown") String fromTown, @Param("toTown") String toTown);

}
