package com.app.bus.booking;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = {"com.app.bus.booking.*"})
public class BusBookingServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusBookingServiceApplication.class, args);}

  @Bean
  public ModelMapper modelMapper()
  {
    return new ModelMapper();
  }
}
