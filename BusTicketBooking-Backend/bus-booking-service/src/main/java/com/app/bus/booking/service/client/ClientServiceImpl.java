package com.app.bus.booking.service.client;

import com.app.bus.booking.domain.user.Ad;
import com.app.bus.booking.domain.user.Reservation;
import com.app.bus.booking.domain.user.Review;
import com.app.bus.booking.domain.user.User;
import com.app.bus.booking.dto.AdDTO;
import com.app.bus.booking.dto.AdDetailsForClientDTO;
import com.app.bus.booking.dto.ReservationDTO;
import com.app.bus.booking.dto.ReviewDTO;
import com.app.bus.booking.enums.ReservationStatus;
import com.app.bus.booking.enums.ReviewStatus;
import com.app.bus.booking.repository.AdRepository;
import com.app.bus.booking.repository.ReservationRepository;
import com.app.bus.booking.repository.ReviewRepository;
import com.app.bus.booking.repository.UserRepository;
import com.app.bus.booking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.app.bus.booking.controller.CompanyController.uploadDirectory;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service
public class ClientServiceImpl implements ClientService  {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserService usersService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;



    public List<AdDTO> getAllAds() {
        List<Ad> adList = adRepository.findAll();
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
            User user = userRepository.findById(ad.getUser().getId()).orElse(null);
            if (user != null) {
                dto.setBusNoPlate(user.getBusNoPlate());
                dto.setContactNo(user.getPhone());
                dto.setName(user.getFirstName() + " " + user.getLastName());
            }
            return dto;
        }).collect(Collectors.toList());
    }


    public List<AdDTO> searchAdByFilter(String fromTown, String toTown)
    {
        return adRepository.findAllByFromTownAndToTown(fromTown,toTown).stream().map(ad -> {
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
            User user = userRepository.findById(ad.getUser().getId()).orElse(null);
            if (user != null) {
                dto.setBusNoPlate(user.getBusNoPlate());
                dto.setContactNo(user.getPhone());
                dto.setName(user.getFirstName() + " " + user.getLastName());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean bookService(ReservationDTO reservationDTO){
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if(optionalAd.isPresent() && optionalUser.isPresent())
        {
            Reservation reservation = new Reservation();

            reservation.setBookdate(reservationDTO.getBookDate());
            reservation.setNoOfSeats(reservationDTO.getNoOfSeats());
            reservation.setPickUpLocation(reservationDTO.getPickUpLocation());
            reservation.setReservationStatus(ReservationStatus.NOT_PAID);
            reservation.setUser(optionalUser.get());

            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);
            return true;
        }

        return false;
    }


    public AdDetailsForClientDTO getAdDetailsByAdId(Long adId)
    {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();

        if(optionalAd.isPresent())
        {

            adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDTOClient());

            Path path = Paths.get(uploadDirectory, adRepository.findImagePathByAdId(adId));
            try {
                byte[] imageBytes = Files.readAllBytes(path);
                adDetailsForClientDTO.setImageContentForClient(imageBytes);
            } catch (IOException e) {
                LOGGER.error("Error reading image file", e);
            }

            List<Review> reviewList = reviewRepository.findAllByAdId(adId);
            adDetailsForClientDTO.setReviewDTOList(reviewList.stream().map(Review::getReviewDTO).collect(Collectors.toList()));
        }
        return adDetailsForClientDTO;
    }


    public List<ReservationDTO> getAllBookingsByUserId(Long userId){
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }

    public Boolean giveReview(ReviewDTO reviewDTO){
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());

        if(optionalUser.isPresent() && optionalBooking.isPresent())
        {
            Review review = new Review();

            review.setReviewDate(new Date());
            review.setReview(reviewDTO.getReview());
            review.setRating(reviewDTO.getRating());

            review.setUser(optionalUser.get());
            review.setAd(optionalBooking.get().getAd());

            reviewRepository.save(review);

            Reservation booking = optionalBooking.get();
            booking.setReviewStatus(ReviewStatus.TRUE);

            reservationRepository.save(booking);

            return true;

        }
        return false;
    }
}
