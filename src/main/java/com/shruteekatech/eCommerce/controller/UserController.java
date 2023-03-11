package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.constants.AppConstants;
import com.shruteekatech.eCommerce.utills.ApiResponse;
import com.shruteekatech.eCommerce.utills.Login;
import com.shruteekatech.eCommerce.dto.UserDto;
import com.shruteekatech.eCommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for create new user
     * @param userDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Start : createUser() started from UserController");
        UserDto user = this.userService.createUser(userDto);
        logger.info("Complete : createUser() completed from UserController");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for update new user with help of userId & userDto
     * @param userId
     * @param userDto
     * @return
     * @exception : ResourceNotFoundException
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser( @PathVariable Long userId,@Valid @RequestBody UserDto userDto){
        logger.info("Start : updateUser() started from UserController");
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        logger.info("Complete : updateUser() completed from UserController");
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find the user by userId
     * @param userId
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        logger.info("Start : getUserById() started from UserController");
        UserDto userById = this.userService.findUserById(userId);
        logger.info("Complete : getUserById() completed from UserController");
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for get list of all users
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        logger.info("Start : getAllUser() started from UserController");
        List<UserDto> allUser = this.userService.findAllUser();
        logger.info("Complete : getAllUser() completed from UserController");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for delete user by userId
     * @param userId
     * @return
     * @exception : ResourceNotFoundException
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        logger.info("Start : deleteUser() started from UserController");
        this.userService.deleteUser(userId);
        logger.info("Complete : deleteUser() completed from UserController");
        return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE_SUCCESS,true),HttpStatus.OK);
    }

    // findBy methods Implementations :

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find list of users by name
     * @param name
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/byName/{name}")
    public ResponseEntity<List<UserDto>> findUserByName(@PathVariable String name){
        logger.info("Start : findUserByName() started from UserController");
        List<UserDto> byName = this.userService.findByName(name);
        logger.info("Complete : findUserByName() completed from UserController");
        return new ResponseEntity<List<UserDto>>(byName,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find user by email
     * @param email
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email){
        logger.info("Start : findUserByEmail() started from UserController");
        UserDto byEmail = this.userService.findByEmail(email);
        logger.info("Complete : findUserByEmail() completed from UserController");
        return new ResponseEntity<>(byEmail,HttpStatus.OK);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find user by phone
     * @param phone
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/byPhone/{phone}")
    public ResponseEntity<UserDto> findUserByPhone(@PathVariable String phone){
        logger.info("Start : findUserByPhone() started from UserController");
        UserDto byPhone = this.userService.findByPhone(phone);
        logger.info("Complete : findUserByPhone() completed from UserController");
        return new ResponseEntity<>(byPhone,HttpStatus.OK);
    }

    // findByEmailAndPassword(String email,String passowrd) used for login user

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for login user
                : It's internally called findByEmailAndPassword(String email,String passowrd)
     * @param login
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody Login login) {
        logger.info("Start : loginUser() started from UserController");
        boolean loginStatus = this.userService.loginCheck(login);
        if(loginStatus){
            logger.info("Complete : loginUser() completed from UserController");
            return new ResponseEntity<>(new ApiResponse(AppConstants.LOGIN_SUCCESS,true),HttpStatus.OK);
        }else
            return new ResponseEntity<>(new ApiResponse(AppConstants.LOGIN_FAIL,false),HttpStatus.NOT_FOUND);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find list of users by name containing with any words(infix)
     * @param infix
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/names/{infix}")
    public ResponseEntity<List<UserDto>> findUserByNameContaining(@PathVariable String infix){
        logger.info("Start : findUserByNameContaining() started from UserController");
        List<UserDto> nameContaining = this.userService.findByNameContaining(infix);
        logger.info("Complete : findUserByNameContaining() completed from UserController");
        return new ResponseEntity<>(nameContaining,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find list of users which name starts with any words(prefix)
     * @param prefix
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/nameStrat/{prefix}")
    public ResponseEntity<List<UserDto>> findUserByNameStartingWith(@PathVariable String prefix){
        logger.info("Start : findUserByNameStartingWith() started from UserController");
        List<UserDto> byNameStartingWith = this.userService.findByNameStartingWith(prefix);
        logger.info("Complete : findUserByNameStartingWith() completed from UserController");
        return new ResponseEntity<>(byNameStartingWith,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find all users who are active
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/active")
    public ResponseEntity<List<UserDto>> findUserByIsActiveTrue(){
        logger.info("Start : findUserByIsActiveTrue() started from UserController");
        List<UserDto> activeTrue = this.userService.findByIsActiveTrue();
        logger.info("Complete : findUserByIsActiveTrue() completed from UserController");
        return new ResponseEntity<>(activeTrue,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find all users who are not active
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/notActive")
    public ResponseEntity<List<UserDto>> findUserByIsActiveFalse(){
        logger.info("Start : findUserByIsActiveFalse() started from UserController");
        List<UserDto> activeFalse = this.userService.findByIsActiveFalse();
        logger.info("Complete : findUserByIsActiveFalse() completed from UserController");
        return new ResponseEntity<>(activeFalse,HttpStatus.OK);
    }


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find users by gender which containing any words
     * @param words
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/gender/{words}")
    public ResponseEntity<List<UserDto>> findUserByGenderContaning(@PathVariable String words){
        logger.info("Start : findUserByGenderContaining() started from UserController");
        List<UserDto> byGender = this.userService.findByGenderContaining(words);
        logger.info("Complete : findUserByGenderContaining() completed from UserController");
        return new ResponseEntity<>(byGender,HttpStatus.OK);
    }

}
