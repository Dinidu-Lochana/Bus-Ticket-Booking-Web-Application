package com.app.bus.booking.dto;

import com.app.bus.booking.enums.ReservationStatus;
import com.app.bus.booking.enums.ReviewStatus;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class ReservationDTO {

    private Long id;

    private Date bookDate;

    private String pickUpLocation;

    private int noOfSeats;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private String fromTown;

    private String toTown;

    private Long userId;

    private String userName;

    private String contactNo;

    private Long companyId;

    private Long adId;

    private Double price;

    private Time departureTime;

    private String driverContactNo;

}
