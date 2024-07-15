package com.app.bus.booking.dto;

import com.app.bus.booking.domain.user.Ad;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class ReviewDTO {

    private Long id;

    private Date reviewDate;

    private String review;

    private Long rating;

    private Long userId;

    private Long adId;

    private String clientName;

    private Long bookId;
}
