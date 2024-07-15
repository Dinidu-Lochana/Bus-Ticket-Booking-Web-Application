package com.app.bus.booking.service.company;


import com.app.bus.booking.domain.user.Ad;
import com.app.bus.booking.domain.user.Reservation;
import com.app.bus.booking.domain.user.User;
import com.app.bus.booking.dto.AdDTO;
import com.app.bus.booking.dto.ReservationDTO;
import com.app.bus.booking.enums.ReservationStatus;
import com.app.bus.booking.repository.AdRepository;
import com.app.bus.booking.repository.ReservationRepository;
import com.app.bus.booking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.app.bus.booking.controller.CompanyController.uploadDirectory;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdRepository adRepository;



    @Autowired
    private ReservationRepository reservationRepository;

    public boolean postAd(MultipartFile img, Long userId, AdDTO adDTO) throws IOException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            LOGGER.info("User found with ID: " + userId);

            // Log AdDTO fields
            LOGGER.info("AdDTO Details: " + adDTO.toString());

            Ad ad = new Ad();
            ad.getAdDTO();

            ad.setFromTown(adDTO.getFromTown());
            ad.setToTown(adDTO.getToTown());
            ad.setDepartureTime(adDTO.getDepartureTime());
            ad.setArriveTime(adDTO.getArriveTime());
            ad.setPrice(adDTO.getPrice());
            ad.setAvailableSeats(adDTO.getAvailableSeats());
            ad.setDescription(adDTO.getDescription());
            ad.setImagePath(adDTO.getImagePath());


            ad.setUser(optionalUser.get());
            adRepository.save(ad);

            LOGGER.info("Ad saved successfully");
            return true;
        } else {
            LOGGER.warn("User not found with ID: " + userId);
        }
        return false;
    }


    private static final String uploadDirectory = System.getProperty("user.dir") + "/src/main/java/images";

    public List<AdDTO> getAllAds(Long userId) {
        List<Ad> adList = adRepository.findAllByUserId(userId);
        return adList.stream().map(ad -> {
            AdDTO dto = modelMapper.map(ad, AdDTO.class);
            if (dto.getImagePath() != null && !dto.getImagePath().isEmpty()) {
                Path path = Paths.get(uploadDirectory, dto.getImagePath());
                try {
                    byte[] imageBytes = Files.readAllBytes(path);
                    dto.setImageContent(imageBytes);
                } catch (IOException e) {
                    LOGGER.error("Error reading image file", e);

                }
            }
            return dto;
        }).collect(Collectors.toList());
    }


    public AdDTO getAdById(Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if(optionalAd.isPresent())
        {
            return optionalAd.get().getAdDTO();
        }
        return null;
    }

    public boolean updateAd(Long adId, AdDTO adDTO)
    {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if(optionalAd.isPresent())
        {
            Ad ad = optionalAd.get();

            ad.setFromTown(adDTO.getFromTown());
            ad.setToTown(adDTO.getToTown());
            ad.setDepartureTime(adDTO.getDepartureTime());
            ad.setArriveTime(adDTO.getArriveTime());
            ad.setPrice(adDTO.getPrice());
            ad.setAvailableSeats(adDTO.getAvailableSeats());
            ad.setDescription(adDTO.getDescription());
            adRepository.save(ad);
            return true;
        }
        else{
            return false;
        }

    }

    public boolean deleteAd(Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent())
        {
            adRepository.delete(optionalAd.get());
            return true;
        }
        return false;
    }

    public List<ReservationDTO> getAllAdBookings(Long companyId){
        return reservationRepository.findAllByCompanyId(companyId).stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }

    public boolean changeBookingStatus(Long bookingId, String status)
    {
        Optional<Reservation> optionalReservation = reservationRepository.findById(bookingId);
        if(optionalReservation.isPresent())
        {
            Reservation existingReservation = optionalReservation.get();
            if (Objects.equals(status,"PAID"))
            {
                existingReservation.setReservationStatus(ReservationStatus.PAID);
            }
            reservationRepository.save(existingReservation);
            return true;
        }
        return false;
    }

    public List<ReservationDTO> searchBookingByFilter(Date bookDate )
    {
        return reservationRepository.findAllByBookdate(bookDate ).stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }
}
