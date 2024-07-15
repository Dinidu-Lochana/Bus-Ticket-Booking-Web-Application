package com.app.bus.booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  public String plainRequest() {
    return "Bus Booking Service Is Up and Running!";
  }
}
