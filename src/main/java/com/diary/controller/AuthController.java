package com.diary.controller;

import com.diary.model.User;
import com.diary.service.AuthService;
import com.diary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        if (!userService.doesUserExist(user)) {
            userService.save(user);
        } else {
            return ResponseEntity.badRequest().body("User already exists. Please find your token :)");
        }
        return ResponseEntity.ok(authService.generateToken(user.getUsername()));
    }
}
