package com.app.bus.booking.domain.user;

import com.app.bus.booking.dto.AdDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;

import static com.app.bus.booking.controller.CompanyController.uploadDirectory;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="ads")
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromTown;

    private String toTown;

    private Double price;

    private Time departureTime;

    private Time arriveTime;

    private String description;

    private int availableSeats;

    private String imagePath;





    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;



    public AdDTO getAdDTO()
    {
        AdDTO adDTO = new AdDTO();

        adDTO.setId(id);
        adDTO.setFromTown(fromTown);
        adDTO.setToTown(toTown);
        adDTO.setDepartureTime(departureTime);
        adDTO.setArriveTime(arriveTime);
        adDTO.setPrice(price);
        adDTO.setDescription(description);
        adDTO.setAvailableSeats(availableSeats);
        //adDTO.setReturnedImg(img);


        return adDTO;
    }

    public AdDTO getAdDTOClient()
    {
        AdDTO adDTO = new AdDTO();

        adDTO.setId(id);
        adDTO.setFromTown(fromTown);
        adDTO.setToTown(toTown);
        adDTO.setDepartureTime(departureTime);
        adDTO.setArriveTime(arriveTime);
        adDTO.setPrice(price);
        adDTO.setDescription(description);
        adDTO.setAvailableSeats(availableSeats);
        adDTO.setBusNoPlate(user.getBusNoPlate());
        adDTO.setContactNo(user.getPhone());
        adDTO.setName(user.getFirstName()+ " " + user.getLastName());

        return adDTO;
    }
}
