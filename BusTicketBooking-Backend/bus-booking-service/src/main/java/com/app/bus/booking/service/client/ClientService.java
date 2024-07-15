package com.app.bus.booking.service.client;

import com.app.bus.booking.dto.AdDTO;
import com.app.bus.booking.dto.AdDetailsForClientDTO;
import com.app.bus.booking.dto.ReservationDTO;
import com.app.bus.booking.dto.ReviewDTO;

import java.util.List;

public interface ClientService {

    List<AdDTO> getAllAds();

    List<AdDTO> searchAdByFilter(String fromTown, String toTown);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);

    Boolean giveReview(ReviewDTO reviewDTO);
}
