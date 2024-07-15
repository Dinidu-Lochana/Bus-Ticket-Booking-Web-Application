package com.app.bus.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdDTO {

    private Long id;
    private String fromTown;
    private String toTown;
    private Double price;
    private int availableSeats;
    private Time departureTime;
    private Time arriveTime;
    private String imagePath;
    private Long userId;
    private String description;
    private byte[] imageContent;

    private String busNoPlate;
    private String contactNo;
    private String name;


}
