package com.app.bus.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdDetailsForClientDTO {

    private AdDTO adDTO;

    private byte[] imageContentForClient;

    private List<ReviewDTO> reviewDTOList;
}
