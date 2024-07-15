package com.app.bus.booking.service.company;

import com.app.bus.booking.dto.AdDTO;
import com.app.bus.booking.dto.ReservationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface CompanyService {
    boolean postAd(MultipartFile img, Long userId , AdDTO adDTO) throws IOException;
    List<AdDTO> getAllAds(Long userId);

    AdDTO getAdById(Long adId);

    boolean updateAd(Long adId, AdDTO adDTO);

    boolean deleteAd(Long adId);

    List<ReservationDTO> getAllAdBookings(Long companyId);

    boolean changeBookingStatus(Long bookingId, String status);

    List<ReservationDTO> searchBookingByFilter(Date date);
}
