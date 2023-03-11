package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.dto.UserDto;
import com.shruteekatech.eCommerce.security.Jwt2Helper;
import com.shruteekatech.eCommerce.service.UserService;
import com.shruteekatech.eCommerce.utills.ApiResponse;
import com.shruteekatech.eCommerce.utills.JwtResponse;
import com.shruteekatech.eCommerce.utills.Login;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private Jwt2Helper jwtHelper;

    @Autowired
    private ModelMapper modelMapper;

//    @GetMapping("/currentUser")
//    public ResponseEntity<String> getCurrentUser(Principal principal){
//        String name = principal.getName();
//        return new ResponseEntity<>(name, HttpStatus.OK);
//    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<UserDetails> getCurrentUser(Principal principal){
//        String name = principal.getName();
//        return new ResponseEntity<>(userDetailsService.loadUserByUsername(name), HttpStatus.OK);
//    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        String name = principal.getName();
        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name), UserDto.class ),HttpStatus.OK);
    }

    @PostMapping("/loginByJWT")
    public ResponseEntity<JwtResponse> loginUserByJWT(@RequestBody Login login) {
        logger.info("Start : loginUserByJWT() started from loginUserByJWT");
        // authenticate user
        this.doAuthenticate(login.getEmail(), login.getPassword());
        // load user from userDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        // getToken from jwtHelper
        String token = this.jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto).build();

        logger.info("Completed : loginUserByJWT() completed from loginUserByJWT");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email,String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
        try
        {
            manager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid User Name Or Password");
        }

    }
}
