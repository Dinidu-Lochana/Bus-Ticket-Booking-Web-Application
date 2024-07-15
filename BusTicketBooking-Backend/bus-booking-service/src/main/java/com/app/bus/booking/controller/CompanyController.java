package com.app.bus.booking.controller;

import com.app.bus.booking.dto.AdDTO;
import com.app.bus.booking.dto.ReservationDTO;
import com.app.bus.booking.repository.AdRepository;
import com.app.bus.booking.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/java/images";

    @PostMapping("/ad/{userId}")
    public ResponseEntity<?> postAd(@RequestParam("image") MultipartFile file, @PathVariable Long userId, @ModelAttribute AdDTO adDTO) throws IOException {
        String originalFileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
        Files.write(fileNameAndPath, file.getBytes());

        adDTO.setImagePath(originalFileName);

        try {
            boolean success = companyService.postAd(file, userId, adDTO);
            if (success) {
                return ResponseEntity.status(HttpStatus.OK).body("Ad posted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while posting the ad");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getBindingResult().getAllErrors());
    }

    @GetMapping("/ads/{userId}")
    public List<AdDTO> getAllAdsByUserId(@PathVariable Long userId)
    {
        return companyService.getAllAds(userId);
    }


    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdById(@PathVariable Long adId)
    {
        AdDTO adDTO = companyService.getAdById(adId);
        if (adDTO != null)
        {
            return ResponseEntity.ok(adDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/ad/{adId}")
    public ResponseEntity<?> updateAd(@PathVariable Long adId, @ModelAttribute AdDTO adDTO)
    {
        boolean success = companyService.updateAd(adId , adDTO);
        if(success)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/ad/{adId}")
    public ResponseEntity<?> deleteAd(@PathVariable Long adId)
    {
        boolean success =companyService.deleteAd(adId);
        if (success)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/bookings/{companyId}")
    public ResponseEntity<List<ReservationDTO>> getAllAdBookings(@PathVariable Long companyId)
    {
        return ResponseEntity.ok(companyService.getAllAdBookings(companyId));
    }

    @GetMapping("/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status)
    {
        boolean success = companyService.changeBookingStatus(bookingId, status);
        if (success)
        {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{bookDate}")
    public ResponseEntity<?> searchBookingByFiltering(@PathVariable("bookDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date bookDate)
    {
        return ResponseEntity.ok(companyService.searchBookingByFilter(bookDate));
    }




}
