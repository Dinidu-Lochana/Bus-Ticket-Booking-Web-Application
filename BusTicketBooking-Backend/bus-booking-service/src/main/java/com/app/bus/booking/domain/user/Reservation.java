package com.app.bus.booking.domain.user;

import com.app.bus.booking.dto.ReservationDTO;
import com.app.bus.booking.enums.ReservationStatus;
import com.app.bus.booking.enums.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private Date bookdate;

    private int noOfSeats;

    private String pickUpLocation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="company_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="ad_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ad ad;


    public ReservationDTO getReservationDTO(){
        ReservationDTO dto = new ReservationDTO();

        dto.setId(id);
        dto.setFromTown(ad.getFromTown());
        dto.setToTown(ad.getToTown());
        dto.setBookDate(bookdate);
        dto.setNoOfSeats(noOfSeats);
        dto.setPickUpLocation(pickUpLocation);
        dto.setReservationStatus(reservationStatus);
        dto.setReviewStatus(reviewStatus);

        dto.setAdId(ad.getId());
        dto.setCompanyId(company.getId());
        dto.setUserName(user.getFirstName() + " "+ user.getLastName());
        dto.setContactNo(user.getPhone());
        dto.setUserId(user.getId());
        dto.setPrice(noOfSeats* ad.getPrice());
        dto.setDepartureTime(ad.getDepartureTime());
        dto.setDriverContactNo(company.getPhone());
        return dto;
    }

}
