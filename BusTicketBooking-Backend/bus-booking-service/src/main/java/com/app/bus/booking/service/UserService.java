package com.app.bus.booking.service;

import com.app.bus.booking.domain.user.User;

public interface UserService {
     User signupClient(User user);
     User signupCompany(User user);

     User getUserByEmail(String email);
}
